import {ADD_TO_TICKET } from '../constants/constants'


const data =[
    {id: 2, cantidad: 1, producto: "Coca Cola 700ml", precio: 10},
    {id: 3, cantidad: 1, producto: "Leche Lala 1L", precio: 17}
]

export default function(state = {products: data}, action) {
    switch (action.type) {

        case ADD_TO_TICKET:
            let sortByDate = state[0].slice(0);
            sortByDate.sort(function(a,b) {
                a.date = new Date(a.date);
                b.date = new Date(b.date);
                return b.date - a.date;
            });
            return [sortByDate];
        case ORDER_BY_RATING:

        default:
            return state;
    }
}