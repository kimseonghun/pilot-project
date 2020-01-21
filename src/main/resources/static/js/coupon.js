function create() {
    const name = document.getElementById('name');
    const issuableDate = document.getElementById('issuable-date');
    const usableDate = document.getElementById('usable-date');
    const price = document.getElementById('price');
    const amount = document.getElementById('amount');

    const data = {};
    data.name = name.value;
    data.issuableDate = issuableDate.value + "T00:00:00";
    data.usableDate = usableDate.value + "T00:00:00";
    data.price = price.value;
    data.amount = amount.value;
    const dataBody = JSON.stringify(data);

    fetch("/api/v1/coupon", {
        method: "POST",
        body: dataBody,
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        }
    })
        .then(response => {
            if (response.status !== 200) {
                const res = response.json();
                res.then(data => signUpError(data));
            } else {
                findAll();
            }
        })
}

function signUpError(errorMessage) {
    alert(errorMessage);
}

const couponTemplate =
    "{{#coupons}}\n" +
    "<li>\n" +
    "     <div>\n" +
    "          <p>{{id}}</p>\n" +
    "          <p>{{name}}</p>\n" +
    "          <p>{{issuableDate}}</p>\n" +
    "          <p>{{usableDate}}</p>\n" +
    "          <p>{{price}}</p>\n" +
    "     </div>\n" +
    "</li>\n" +
    "{{/coupons}}\n";

function findAll() {
    fetch("/api/v1/coupon", {
        method: "GET",
    })
        .then(response => response.json())
        .then(addCoupon)
        .catch(findAllError);
}

function addCoupon(json) {
    const data = json;
    console.log(data);
    const couponItemTemplate = Handlebars.compile(couponTemplate);
    const coupons = document.getElementById('coupons');
    const datas = {
        coupons: data
    };
    coupons.innerHTML = couponItemTemplate(datas);
}

function findAllError(err) {
    alert('오류가 발생했습니다. 다시 시도해주세요');
}

findAll();