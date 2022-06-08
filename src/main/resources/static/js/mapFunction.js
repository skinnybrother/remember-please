const toXY = "toXY";
navigator.geolocation.getCurrentPosition(function (position) {
    //사용자 위치 정보 동의 및 위치 정보 수집

    let dfsXyConv = dfs_xy_conv(toXY,position.coords.latitude, position.coords.longitude);

    function getPcp(pcp) {
        console.log(pcp)
        if(pcp===0.0){
            console.log(pcp);
            $("#pcp").text("없음");
            return "☁";
        }
        //흐림
        if (pcp > 1) {
            $("#pcp").text(pcp);
            return "🌧"
        }
    }

//Ajax Call을 통해, 사용자 위치 기반 날씨 정보 수집 및 출력
    $.ajax({
        type: 'get',
        url: "/weather",
        data: {
            x: dfsXyConv['x'],
            y: dfsXyConv['y'],
            latitude: position.coords.latitude,
            longitude: position.coords.longitude
        },
        success: function (data) {
            $("#temp").text(data.shortForecast.t1h);
            let locationArr = [data.state,data.city,data.town];
            $("#location").text(locationArr.join(" "));
            let sky = data.vilageForecast.sky;
            let pcp = data.vilageForecast.pcp;
            if(sky<3){
                $("#weather").text("🌞");
                //맑음
            }else if(3<=sky<4){
                //구름 많음
                $("#weather").text("⛅");
            }else if(sky>4){
                //강수 정도
                let pcpText = getPcp(pcp);
                $("#weather").text(pcpText);
            }
        },
        error: function (error) {
            console.log(error);
        }
    });
});

//<!--
//
// LCC DFS 좌표변환을 위한 기초 자료
//
let RE = 6371.00877; // 지구 반경(km)
let GRID = 5.0; // 격자 간격(km)
let SLAT1 = 30.0; // 투영 위도1(degree)
let SLAT2 = 60.0; // 투영 위도2(degree)
let OLON = 126.0; // 기준점 경도(degree)
let OLAT = 38.0; // 기준점 위도(degree)
let XO = 43; // 기준점 X좌표(GRID)
let YO = 136; // 기1준점 Y좌표(GRID)
//
// LCC DFS 좌표변환 ( code : "toXY"(위경도->좌표, v1:위도, v2:경도), "toLL"(좌표->위경도,v1:x, v2:y) )
//


function dfs_xy_conv(code, v1, v2) {
    let DEGRAD = Math.PI / 180.0;
    let RADDEG = 180.0 / Math.PI;

    let re = RE / GRID;
    let slat1 = SLAT1 * DEGRAD;
    let slat2 = SLAT2 * DEGRAD;
    let olon = OLON * DEGRAD;
    let olat = OLAT * DEGRAD;

    let sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5);
    sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn);
    let sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5);
    sf = Math.pow(sf, sn) * Math.cos(slat1) / sn;
    let ro = Math.tan(Math.PI * 0.25 + olat * 0.5);
    ro = re * sf / Math.pow(ro, sn);
    let rs = {};
    if (code === toXY) {
        rs['lat'] = v1;
        rs['lng'] = v2;
        let ra = Math.tan(Math.PI * 0.25 + (v1) * DEGRAD * 0.5);
        ra = re * sf / Math.pow(ra, sn);
        let theta = v2 * DEGRAD - olon;
        if (theta > Math.PI) theta -= 2.0 * Math.PI;
        if (theta < -Math.PI) theta += 2.0 * Math.PI;
        theta *= sn;
        rs['x'] = Math.floor(ra * Math.sin(theta) + XO + 0.5);
        rs['y'] = Math.floor(ro - ra * Math.cos(theta) + YO + 0.5);
    }
    else {
        rs['x'] = v1;
        rs['y'] = v2;
        let xn = v1 - XO;
        let yn = ro - v2 + YO;
        ra = Math.sqrt(xn * xn + yn * yn);
        if (sn < 0.0) - ra;
        let alat = Math.pow((re * sf / ra), (1.0 / sn));
        alat = 2.0 * Math.atan(alat) - Math.PI * 0.5;

        if (Math.abs(xn) <= 0.0) {
            theta = 0.0;
        }
        else {
            if (Math.abs(yn) <= 0.0) {
                theta = Math.PI * 0.5;
                if (xn < 0.0) - theta;
            }
            else theta = Math.atan2(xn, yn);
        }
        let alon = theta / sn + olon;
        rs['lat'] = alat * RADDEG;
        rs['lng'] = alon * RADDEG;
    }
    return rs;
}
//-->