import * as types from '../constants/constants'
import * as HotelsAPI from '../utils/HotelsAPI'
import * as LoginAPI from '../utils/LoginAPI'


export const fetchHotels = (city) => dispatch =>
    HotelsAPI.getHotels(city).then(hotels => dispatch({type: types.GET_HOTELS, hotels: hotels}));

export const likeHotel = (user,hotelId) => dispatch =>
    HotelsAPI.likeHotel(user,hotelId);

export const login = (data) => dispatch =>
    LoginAPI.login(data).then(user => dispatch({type: types.LOGIN_USER, user: user}));



export const addToTicket = () => dispatch =>
    dispatch({type: types.ADD_TO_TICKET});




