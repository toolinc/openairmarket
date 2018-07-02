
import { combineReducers } from 'redux'
import { routerReducer } from 'react-router-redux'
import hotels from './HotelReducer'
import user from './LoginReducer'

const rootReducer = combineReducers({
    router: routerReducer,
    user: user,
    hotels: hotels,
})
  
export default rootReducer