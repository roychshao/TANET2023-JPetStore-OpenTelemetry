import http from 'k6/http';
import { group, check } from 'k6';

export default function () {

    let cookieJar = http.cookieJar();

    group('Browse', function () {

        let RootResponse = http.get('http://localhost:8080/jpetstore', { cookieJar: cookieJar });
        check(RootResponse, {
            'Root status is 200': (r) => r.status === 200
        });

        let CategoryResponse = http.get('http://localhost:8080/jpetstore/actions/Catalog.action;jsessionid=E68F50F7D1751F30A877A3FA2E710D11?viewCategory=&categoryId=FISH', { cookieJar: cookieJar });
        check(CategoryResponse, {
            'Category status is 200': (r) => r.status === 200
        });

        let ProductResponse = http.get('http://localhost:8080/jpetstore/actions/Catalog.action?viewProduct=&productId=FI-FW-01', { cookieJar: cookieJar });
        check(ProductResponse, {
            'Product status is 200': (r) => r.status === 200
        });

        let ItemResponse = http.get('http://localhost:8080/jpetstore/actions/Catalog.action?viewItem=&itemId=EST-4', { cookieJar: cookieJar });
        check(ItemResponse, {
            'Item status is 200': (r) => r.status === 200
        });

    })
}
