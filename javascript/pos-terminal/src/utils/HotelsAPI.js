const api = "http://localhost:7050"
// const api = "https://hotelbackend.appspot.com"

export const getHotels = (city) =>
     fetch(`${api}/hotelInfo?city=${city}`)
        .then(res => res.json())
        .then(data => data)
        .catch(function (error) {
            console.log('Request failed', error);
        });

export const likeHotel = (user, hotelId) =>
    fetch(`${api}/hotelLike?username=${user}&hotelId=${hotelId}`,{
        method: 'POST'
    })
        .then(res => res.json())
        .then(data => data)
        .catch(function (error) {
            console.log('Request failed', error);
        });
