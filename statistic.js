const {resolve} = require('dns');
const fs = require('fs');


var MemoryUsages = [];
var CpuTimes = [];
var meanMem = 0;
var meanCpu = 0;
var medianMem = 0;
var medianCpu = 0;
var p99Mem = 0;
var p99Cpu = 0;
var p95Mem = 0;
var p95Cpu = 0;


function printErr(err) {
    console.log('err: ' + err);
}

function getGuages() {
    const filePath = 'result.json';

    fs.readFile(filePath, 'utf8', async (err, data) => {
        if (err) {
            console.error('reading file error: ', err);
        } else {
            try {
                const metricsData = JSON.parse(data);
                for (let i = 0; i < metricsData.length; ++i) {
                    var gauge = metricsData[i];
                    MemoryUsages.push(gauge.resourceMetrics[0].scopeMetrics[1].metrics[0].gauge.dataPoints[0].asDouble);
                    CpuTimes.push(gauge.resourceMetrics[0].scopeMetrics[1].metrics[1].gauge.dataPoints[0].asDouble);
                }

                // sort gauges
                MemoryUsages.sort((a, b) => a - b);
                CpuTimes.sort((a, b) => a - b);
                console.log("MemoryUsages: " + MemoryUsages);
                console.log("CpuTimes: " + CpuTimes);

                // calculate statistic datas
                await calMean().then().catch(printErr);
                await calMedian().then().catch(printErr);
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
            var totalCpuTime = 0;
            for(let i = 0; i < MemoryUsages.length; ++i) {
                totalMemoryUsage += MemoryUsages[i];
                totalCpuTime += CpuTimes[i];
            }
            meanMem = totalMemoryUsage / totalnum;
            meanCpu = totalCpuTime / totalnum;
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
        medianCpu = CpuTimes.length % 2 === 0
            ? (CpuTimes[middleIndex - 1] + CpuTimes[middleIndex]) / 2
            : CpuTimes[middleIndex];
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
            p99Cpu = CpuTimes[p99Index];
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
            p95Cpu = CpuTimes[p95Index];
            resolve();
        } catch (err) {
            reject(err);
        }
    })
}

function Gauge(Mem, Cpu) {
  this.Memory_Usage = Mem;
  this.CPU_Time = Cpu;
}

function printStatistic() {

    var statistic = {};
    statistic.mean = new Gauge(meanMem, meanCpu);
    statistic.median = new Gauge(medianMem, medianCpu);
    statistic.p99 = new Gauge(p99Mem, p99Cpu);
    statistic.p95 = new Gauge(p95Mem, p95Cpu);

    console.table(statistic);
    // console.log("meanMemory: " + meanMem);
    // console.log("meanCPU: " + meanCpu);
    // console.log("medianMem: " + medianMem);
    // console.log("medianCpu: " + medianCpu);
    // console.log("p99Mem: " + p99Mem);
    // console.log("p99Cpu: " + p99Cpu);
    // console.log("p95Mem: " + p99Mem);
    // console.log("p95Cpu: " + p99Cpu);
}

getGuages();
