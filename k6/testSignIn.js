import http from 'k6/http';
import { group, check } from 'k6';

export default function () {

    // 在 group 外部定義 cookieJar
    let cookieJar = http.cookieJar();

    group('SignIn', function () {

        let SignInBody = {
            'username': "ACID",
            'password': "ACID",
            'signon': "Login",
            '_sourcePage': "pMm6fhhRzr0zHOPf7OpnD5FrvSa0gfkxworKRFpcKI2GB3Dg7dKlag4fSzyU9prYemx0El0K5EBMtrKx1QgwtwiLkZ8-n45JrHwYRNoeBvE=",
            '__fp': "h3vPdhit5RhC9BDaBp4fPO6k20MB9_XLOezYVeFuVKk8a8kE8rTAXgE2fmbWgWph",
            cookieJar: cookieJar
        };

        // 使用 cookieJar 發送請求
        let SignInResponse = http.post('http://localhost:8080/jpetstore/actions/Account.action', SignInBody);
        check(SignInResponse, {
            'SignIn status is 200': (r) => r.status === 200
        });
    });
}
