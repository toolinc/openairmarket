import {applyMiddleware, createSote} from "redux"
import logger from "redux-logger"
import thunk from "redux-thunk"
import promise from "redux-promise-middleware"
import reducer from "./reducers"

const middleware = applyMiddleware(promise(), thunk, logger());

export default createSote(reducer, middleware);







// import { createStore, applyMiddleware, compose } from 'redux'
// //import { routerMiddleware } from 'react-router-redux'
// import thunk from 'redux-thunk'
// import { createLogger } from 'redux-logger'
// import createHistory from 'history/createBrowserHistory'
// import rootReducer from './reducers'
//
// export const history = createHistory()
//
// const initialState = {}
// const enhancers = []
// const middleware = [
//   thunk
//   //routerMiddleware(history)
// ]
//
// if (process.env.NODE_ENV !== 'production') {
//   enhancers.push(createLogger())
// }
//
// if (process.env.NODE_ENV === 'development') {
//   const devToolsExtension = window.devToolsExtension
//   if (typeof devToolsExtension === 'function') {
//     enhancers.push(devToolsExtension())
//   }
// }
//
// const composedEnhancers = compose(
//   applyMiddleware(...middleware),
//   ...enhancers
// )
//
// export const store = createStore(
//   rootReducer,
//   initialState,
//   composedEnhancers
// )



//export default store

// import logger from "redux-logger"
// import promise from "redux-promise-middleware"
// import reducer from "./reducers"
//
// const middleware = applyMiddleware(promise(), thunk, logger());
//
// export default createSote(reducer, middleware);