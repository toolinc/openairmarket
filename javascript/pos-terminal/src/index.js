import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './components/App';
import {BrowserRouter} from 'react-router-dom'
import registerServiceWorker from './registerServiceWorker';
import MuiThemeProvider from '@material-ui/core/styles/MuiThemeProvider';
import { createStore, applyMiddleware } from 'redux';
import reducer from './reducers';
import { Provider } from 'react-redux';
import thunk from 'redux-thunk';
import { createLogger } from 'redux-logger'
import { createMuiTheme } from '@material-ui/core/styles';


const middleware = [ thunk ]
if (process.env.NODE_ENV !== 'production') {
    middleware.push(createLogger())
}

const store = createStore(
    reducer,
    applyMiddleware(...middleware)
)

const theme = createMuiTheme({
    palette: {
        // type: 'dark',
        primary: {
            light: '#B2EBF2',
            main: '#00BCD4',
            dark: '#0097A7',
            contrastText: '#fff',
        },
        secondary: {
            light: '#ff7961',
            main: '#f44336',
            dark: '#ba000d',
            contrastText: '#000',
        },
    },
    // palette: {
    //     type: 'dark',
    //     primary: green,
    //     secondary: blue,
    // },
    status: {
        danger: 'orange',
    },
});


ReactDOM.render(<MuiThemeProvider theme={theme}><Provider store={store}><BrowserRouter><App /></BrowserRouter></Provider></MuiThemeProvider>, document.getElementById('root'));
registerServiceWorker();
