const api = "http://localhost:7050"
// const api = "https://hotelbackend.appspot.com"


export const login = (data) => {
    return fetch(`${api}/login?username=` + data.username + '&password=' + data.password, {
        method: 'POST',
        body: data
    })
        .then(res => {
            return res.json();
        })
        .then(data => {
            console.log(data)
            document.cookie = "JSESSIONID=" + data.JSESSIONID
            document.cookie = "user=" + data.username
            document.cookie = "timestamp=" + data.lastLogin
            return data;
        })
        .catch(function (error) {
            console.log('Request failed', error);
        });
}

export const singup = (data) =>
    fetch(`${api}/login?username=` + data.username + '&password=' + data.password)
        .then(res => res.json())
        .then(data => data)
        .catch(function (error) {
            console.log('Request failed', error);
        });

// export const singup = (data) =>
//     fetch(`${api}/login?username=` + data.username + '&password=' + data.password, {
//         method: 'PUT',
//         body: data
//     })
//         .then(res => res.json())
//         .then(data => data)
//         .catch(function (error) {
//             console.log('Request failed', error);
//         });