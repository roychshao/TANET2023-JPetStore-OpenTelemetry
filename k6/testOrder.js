import http from 'k6/http';
import { group, check } from 'k6';

export default function () {

    let cookieJar = http.cookieJar();

    group('Make Order', function () {

        let AddToCartResponse = http.get('http://localhost:8080/jpetstore/actions/Cart.action?addItemToCart=&workingItemId=EST-4', { cookieJar: cookieJar });
        check(AddToCartResponse, {
            'AddToCart status is 200': (r) => r.status === 200
        });

        /*
          private String username;
          private Date orderDate;
          private String shipAddress1;
          private String shipAddress2;
          private String shipCity;
          private String shipState;
          private String shipZip;
          private String shipCountry;
          private String billAddress1;
          private String billAddress2;
          private String billCity;
          private String billState;
          private String billZip;
          private String billCountry;
          private String courier;
          private BigDecimal totalPrice;
          private String billToFirstName;
          private String billToLastName;
          private String shipToFirstName;
          private String shipToLastName;
          private String creditCard;
          private String expiryDate;
          private String cardType;
          private String locale;
          private String status;
          */
        let GenerateNewOrderBody = {
            'order.username': "ACID",
            'order.orderDate': "2023-06-23",
            'order.shipAddress1': '901 San Antonio Road',
            'order.shipAddress2': 'MS UCUP02-206',
            'order.shipCity': 'Palo Alto',
            'order.shipState': 'CA',
            'order.shipZip': 94303,
            'order.shipCountry': 'USA',
            'order.courier': 'UPS',
            'order.totalPrice': '18.50',
            'order.shipToFirstName': 'ABC',
            'order.shipToLastName': 'XYX',
            'order.locale': 'CA',
            'order.status': 'P',

            'order.cardType': "Visa",
            'order.creditCard': "999+9999+9999+9999",
            'order.expiryDate': "12/03",
            'order.billToFirstName': "ABC",
            'order.billToLastName': "XYX",
            'order.billAddress1': "901+San+Antonio+Road",
            'order.billAddress2': "MS+UCUP02-206",
            'order.billCity': "Palo+Alto",
            'order.billState': "CA",
            'order.billZip': "94303",
            'order.billCountry': "USA",
            'newOrder': "Continue",
            '_sourcePage': "PUolIedGg5OafeR1uTkoUZddRDHw4nPBd0FOPTLyXSgsPhUPGTp0KeMuiCG8Gz8x_sAu0Bxi61t-SQX__PZwIcI9gsej5OVl95CbAat7-4c=",
            '__fp': "qfprwnhzR4DyxcyQQOUjNg8lZKYJKzcsDMo_g10-k6tZUd57PPEFO5UsToxRSetGhmpSEB3H0l8Ob8mvoBws_Egfj8dUAR4s85OmR9LESV1fHPV8pD27ww==",
            cookieJar: cookieJar
        };
        let GenerateNewOrderResponse = http.post('http://localhost:8080/jpetstore/actions/Order.action', GenerateNewOrderBody);
        check(GenerateNewOrderResponse, {
            'GenerateNewOrder status is 200': (r) => r.status === 200
        });

        let ConfirmNewOrderResponse = http.get('http://localhost:8080/jpetstore/actions/Order.action?newOrder=&confirmed=true', { cookieJar: cookieJar });
        check(ConfirmNewOrderResponse, {
            'ConfirmNewOrder status is 200': (r) => r.status === 200
        });
    })
}
