const {resolve} = require('dns');
const fs = require('fs');


var MemoryUsages = [];
var CpuUsages = [];
var totalCpuTime = 0;
var meanMem = 0;
var meanCpu = 0;
var q1Mem = 0;
var q1Cpu = 0;
var medianMem = 0;
var medianCpu = 0;
var q3Mem = 0;
var q3Cpu = 0;
var p99Mem = 0;
var p99Cpu = 0;
var p95Mem = 0;
var p95Cpu = 0;


function printErr(err) {
    console.log('err: ' + err);
}

function getGuages() {
    const filePath = 'testBrowse.json';

    fs.readFile(filePath, 'utf8', async (err, data) => {
        if (err) {
            console.error('reading file error: ', err);
        } else {
            try {
                const metricsData = JSON.parse(data);
                for (let i = 0; i < metricsData.length; ++i) {
                    var gauge = metricsData[i];
                    MemoryUsages.push(gauge.resourceMetrics[0].scopeMetrics[0].metrics[0].gauge.dataPoints[0].asDouble);
                    CpuUsages.push(gauge.resourceMetrics[0].scopeMetrics[0].metrics[1].gauge.dataPoints[0].asDouble / 10000);
                }

                // sort gauges
                MemoryUsages.sort((a, b) => a - b);
                CpuUsages.sort((a, b) => a - b);
                console.log("MemoryUsages: " + MemoryUsages);
                console.log("CpuUsages: " + CpuUsages);

                // calculate statistic datas
                await calMean().then().catch(printErr);
                await calq1().then().catch(printErr);
                await calMedian().then().catch(printErr);
                await calq3().then().catch(printErr);
                await calp99().then().catch(printErr);
                await calp95().then().catch(printErr);
                printStatistic();
            } catch (err) {
                console.error('err: ', err);
            }
        }
    });
}

function calMean() {
    return new Promise((resolve, reject) => {

        try {
            var totalnum = MemoryUsages.length;
            var totalMemoryUsage = 0;
            var totalCpuUsage = 0;
            for(let i = 0; i < MemoryUsages.length; ++i) {
                totalMemoryUsage += MemoryUsages[i];
                totalCpuUsage += CpuUsages[i];
            }
            meanMem = totalMemoryUsage / totalnum;
            meanCpu = totalCpuUsage / totalnum;
            resolve();
        } catch (err) {
            reject(err);
        }
    })
}

function calMedian() {
    return new Promise((resolve, reject) => {
        try {
        var middleIndex = Math.floor(MemoryUsages.length / 2);
        medianMem = MemoryUsages.length % 2 === 0
            ? (MemoryUsages[middleIndex - 1] + MemoryUsages[middleIndex]) / 2
            : MemoryUsages[middleIndex];
        medianCpu = CpuUsages.length % 2 === 0
            ? (CpuUsages[middleIndex - 1] + CpuUsages[middleIndex]) / 2
            : CpuUsages[middleIndex];
            resolve();
        } catch (err) {
            reject(err);
        }
    })
}

function calq1() {
    return new Promise((resolve, reject) => {
        try {
            var q1Index = Math.ceil(MemoryUsages.length * 0.25) - 1;
            q1Mem = MemoryUsages[q1Index];
            q1Cpu = CpuUsages[q1Index];
            resolve();
        } catch (err) {
            reject(err);
        }
    })
}

function calq3() {
    return new Promise((resolve, reject) => {
        try {
            var q3Index = Math.ceil(MemoryUsages.length * 0.75) - 1;
            q3Mem = MemoryUsages[q3Index];
            q3Cpu = CpuUsages[q3Index];
            resolve();
        } catch (err) {
            reject(err);
        }
    })
}
function calp99() {
    return new Promise((resolve, reject) => {
        try {
            var p99Index = Math.ceil(MemoryUsages.length * 0.99) - 1;
            p99Mem = MemoryUsages[p99Index];
            p99Cpu = CpuUsages[p99Index];
            resolve();
        } catch (err) {
            reject(err);
        }
    })
}

function calp95() {
    return new Promise((resolve, reject) => {
        try {
            var p95Index = Math.ceil(MemoryUsages.length * 0.95) - 1;
            p95Mem = MemoryUsages[p95Index];
            p95Cpu = CpuUsages[p95Index];
            resolve();
        } catch (err) {
            reject(err);
        }
    })
}

function Gauge(Mem, Cpu) {
  this.Memory_Usage = Mem;
  this.CPU_Usage = Cpu;
}

function printStatistic() {

    var statistic = {};
    statistic.Mean = new Gauge(meanMem, meanCpu);
    statistic.Q1 = new Gauge(q1Mem, q1Cpu);
    statistic.Median = new Gauge(medianMem, medianCpu);
    statistic.Q3 = new Gauge(q3Mem, q3Cpu);
    statistic.P95 = new Gauge(p95Mem, p95Cpu);
    statistic.P99 = new Gauge(p99Mem, p99Cpu);

    console.table(statistic);
}

getGuages();
