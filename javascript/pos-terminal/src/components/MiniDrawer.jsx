import React from 'react';
import PropTypes from 'prop-types';
import classNames from 'classnames';


import {withStyles} from '@material-ui/core/styles';
import Drawer from '@material-ui/core/Drawer';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import Divider from '@material-ui/core/Divider';
import IconButton from '@material-ui/core/IconButton';
import MenuIcon from '@material-ui/icons/Menu';
import Reply from '@material-ui/icons/Reply';
import ChevronRightIcon from '@material-ui/icons/ChevronRight';
import MenuItem from '@material-ui/core/MenuItem';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import Button from '@material-ui/core/Button';
import Snackbar from '@material-ui/core/Snackbar';
import CloseIcon from '@material-ui/icons/Close';

import ListItemIcon from '@material-ui/core/ListItemIcon';
import InboxIcon from '@material-ui/icons/MoveToInbox';

import Menu from '@material-ui/core/Menu';
import TextField from '@material-ui/core/TextField';
import InputAdornment from '@material-ui/core/InputAdornment';


import ShoppingCart from '@material-ui/icons/ShoppingCart';
import Receipt from '@material-ui/icons/Receipt';

import Search from '@material-ui/icons/Search';
import AccountCircle from '@material-ui/icons/AccountCircle';

import Home from './Home';
import Ticket from './Ticket';
import dataFile from '../data';


const drawerWidth = 190;
const drawerWidthTicket = 550;


function TabContainer(props) {
    return (
        <Typography component="div" style={{padding: 8 * 3}}>
            {props.children}
        </Typography>
    );
}

TabContainer.propTypes = {
    children: PropTypes.node.isRequired,
};

const styles = theme => ({
    menu: {
        position: 'relative',
    },
    root: {
        flexGrow: 1,
        zIndex: 1,
        overflow: 'hidden',
        position: 'relative',
        display: 'flex',
    },
    appBar: {
        zIndex: theme.zIndex.drawer + 1,
        transition: theme.transitions.create(['width', 'margin'], {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.leavingScreen,
        }),
        height: '67px'
    },
    appBarShift: {
        marginLeft: drawerWidth,
        width: `calc(100% - ${drawerWidth}px)`,
        transition: theme.transitions.create(['width', 'margin'], {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.enteringScreen,
        }),
    },
    menuButton: {
        marginLeft: 12,
        marginRight: 36,
    },
    hide: {
        display: 'none',
    },
    ticketDrawer: {
        height: '500px',
        width: drawerWidthTicket,
    },
    drawerPaper: {
        position: 'relative',
        whiteSpace: 'nowrap',
        width: drawerWidth,
        transition: theme.transitions.create('width', {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.enteringScreen,
        }),
    },
    drawerPaperTicket: {
        position: 'relative',
        whiteSpace: 'nowrap',
        width: drawerWidthTicket,
    },
    drawerPaperClose: {
        overflowX: 'hidden',
        transition: theme.transitions.create('width', {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.leavingScreen,
        }),
        width: 0,
        [theme.breakpoints.up('sm')]: {
            width: theme.spacing.unit * 0.1,
        },
    },
    toolbar: {
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'flex-end',
        padding: '0 20px',
        ...theme.mixins.toolbar,
    },
    content: {
        flexGrow: 1,
        backgroundColor: theme.palette.background.default,
        marginTop: theme.spacing.unit * 8,
        width: '100%',
    },

    gridList: {
        width: drawerWidthTicket,
        height: '100%',
        // Promote the list into his own layer on Chrome. This cost memory but helps keeping high FPS.
        transform: 'translateZ(0)',
    },

    leftIcon: {
        marginRight: theme.spacing.unit,
    },
    rightIcon: {
        marginLeft: theme.spacing.unit,
    },
    iconSmall: {
        fontSize: 20,
    },
    menuButtonUser: {
        marginLeft: -12,
        marginRight: 20,
    },
    flex: {
        flex: 1,
    },
    textField: {
        marginLeft: theme.spacing.unit,
        marginRight: theme.spacing.unit,
        width: 200,
    },
    bar: {
        marginTop: theme.spacing.unit * 8,
    }

});

let counter = 0;

function createData(cantidad, producto, precio, total, piezas) {
    counter += 1;
    return {id: counter, cantidad, producto, precio, total, piezas};
}

class MiniDrawer extends React.Component {
    state = {
        open: false,
        data0: [],
        data1: [
            createData(1, "Coca Cola 500ml", 7.00, 1, true),
            createData(1, "Coca Cola 700ml", 10.00, 2, true),
            createData(1, "Leche Lala 1L", 17.00, 1, true),
        ],
        data2: [
            createData(1, "Leche Lala 1L", 17.00, 1, true),
            createData(1, "Coca Cola 500ml", 7.00, 2, true),
        ],
        total: 0,
        anchorEl: null,
        openDialog: false,
        value: 0,
    };

    scan = scannedBarcode => {
        dataFile.map(item => {
            if (item.id == scannedBarcode.toString()) {
                console.log("inside " + item.id);
                this.addTicket(item);
            }
        });
    };

    componentDidMount() {
        let self = this;
        var scannedBarcode = '';
        window.onkeypress = function (e) {
            let barcode = "";
            let code = e.keyCode ? e.keyCode : e.which;
            if (code === 13) {
                console.log("DISPATCHING: " + scannedBarcode);
                self.scan(scannedBarcode);
                scannedBarcode = '';
            } else {
                barcode = barcode + String.fromCharCode(code);
                scannedBarcode += barcode;
            }
        }
    }


    handleDrawerOpen = () => {
        this.setState({open: true});
        console.log(this.state.data1)
    };

    handleDrawerClose = () => {
        this.setState({open: false});
    };


    handleClose = (event, value) => {
        console.log(event);
        console.log(value);
        if (value == 'timeout' || event == 'snackbar') {
            this.setState({payment: false});
        }
        this.setState({anchorEl: null});
    };

    handleMenu = event => {
        this.setState({anchorEl: event.currentTarget});
    };

    handleChange = (event, value) => {
        this.setState({value});
    };

    handleCancelTicket = () => {
        if (this.state.value === 0) {
            this.setState({data0: []});
        } else if (this.state.value === 1) {
            this.setState({data1: []});
        } else if (this.state.value === 2) {
            this.setState({data2: []});
        }
    };

    handlePaymentTicket = () => {

        if (this.state.value === 0) {
            this.setState({data0: [], payment: true});
        } else if (this.state.value === 1) {
            this.setState({data1: [], payment: true});
        } else if (this.state.value === 2) {
            this.setState({data2: [], payment: true});
        }
    };

    updateProduct = (product) => {
        console.log(product.c);
        console.log(this.state.value);
        let tempProduct = [];

        if (this.state.value === 0) {
            this.state.data0.map(item => {
                if (item.producto === product.producto) {
                    item.total = product.cantidad;
                }
                if (item.total > 0) {
                    tempProduct.push(item);
                }
            });
            this.setState({
                data0: tempProduct
            });
        } else if (this.state.value === 1) {
            this.state.data1.map(item => {
                if (item.producto === product.producto) {
                    item.total = product.cantidad;
                }
                if (item.total > 0) {
                    tempProduct.push(item);
                }
            });
            this.setState({
                data1: tempProduct
            });

        } else if (this.state.value === 2) {
            this.state.data2.map(item => {
                if (item.producto === product.producto) {
                    item.total = product.cantidad;
                }
                if (item.total > 0) {
                    tempProduct.push(item);
                }
            });
            this.setState({
                data2: tempProduct
            });
        }
    };

    addTicket = (product) => {
        const cantidad = product.cantidad;
        console.log("cantidad " + cantidad);
        let tempProduct = [];
        let added = false;
        if (this.state.value === 0) {
            this.state.data0.map(item => {
                if (item.producto === product.producto) {
                    item.total = parseFloat(item.total) + parseFloat(cantidad);
                    added = true;
                }
                if (parseFloat(item.total) > 0) {
                    tempProduct.push(Object.assign({}, item));
                }
            });
            if (!added) {
                product.total = cantidad;

                if (product.total > 0) {
                    tempProduct.push(Object.assign({}, product));
                }
            }
            this.setState({
                data0: tempProduct
            });

        } else if (this.state.value === 1) {
            this.state.data1.map(item => {
                if (item.producto === product.producto) {
                    item.total = parseFloat(item.total) + parseFloat(cantidad);
                    added = true;

                }
                if (parseFloat(item.total) > 0) {
                    tempProduct.push(Object.assign({}, item));
                }
            });
            if (!added) {
                product.total = cantidad;
                if (product.total > 0) {
                    tempProduct.push(Object.assign({}, product));
                }
            }
            this.setState({
                data1: tempProduct
            });
        } else if (this.state.value === 2) {
            this.state.data2.map(item => {
                if (item.producto === product.producto) {
                    item.total = parseFloat(item.total) + parseFloat(cantidad);
                    added = true;

                }
                if (parseFloat(item.total) > 0) {
                    tempProduct.push(Object.assign({}, item));
                }
            });
            if (!added) {
                product.total = cantidad;
                if (product.total > 0) {
                    tempProduct.push(Object.assign({}, product));
                }
            }
            this.setState({
                data2: tempProduct
            });
        }
    };


    render() {
        const {classes, theme} = this.props;
        const {anchorEl, value} = this.state;
        const open = Boolean(anchorEl);

        return (
            <div id="containerApp" className={classes.root}>
                <AppBar
                    position="absolute"
                    className={classNames(classes.appBar, this.state.open && classes.appBarShift)}
                >
                    <Toolbar disableGutters={!this.state.open}>
                        <IconButton
                            color="inherit"
                            aria-label="open drawer"
                            onClick={this.handleDrawerOpen}
                            className={classNames(classes.menuButton, this.state.open && classes.hide)}
                        >
                            <MenuIcon/>
                        </IconButton>
                        <img type='image/svg' className='ninja animated rubberBand nav-bar-img'
                             src='./../assets/pocket.png' alt=""/>
                        <Typography type="title" color="inherit" className={classes.flex}>
                            AIR<span className='black'> POS</span>
                        </Typography>
                        <TextField
                            id="buscar"
                            label="Buscar"
                            className={classes.textField}
                            type="Buscar"
                            margin="normal"
                            InputProps={{
                                endAdornment: <InputAdornment position="end"><Search/></InputAdornment>,
                            }}
                        />
                        <div>
                            <IconButton
                                aria-owns={open ? 'menu-appbar' : null}
                                aria-haspopup="true"
                                onClick={this.handleMenu}
                                color="inherit"
                                className={classes.menuButtonUser}
                            >
                                <AccountCircle/>
                            </IconButton>
                            <Menu
                                id="menu-appbar"
                                anchorEl={anchorEl}
                                anchorOrigin={{
                                    vertical: 'top',
                                    horizontal: 'right',
                                }}
                                transformOrigin={{
                                    vertical: 'top',
                                    horizontal: 'right',
                                }}
                                open={open}
                                onClose={this.handleClose}
                            >
                                <MenuItem onClick={this.handleClose}>Profile</MenuItem>
                                <MenuItem onClick={this.handleClose}>My account</MenuItem>
                            </Menu>
                        </div>
                    </Toolbar>
                </AppBar>


                <Drawer
                    variant="permanent"
                    id={"menu"}
                    classes={{
                        paper: classNames(classes.menu, classes.drawerPaper, !this.state.open && classes.drawerPaperClose),
                    }}
                    open={this.state.open}
                >
                    <div className={classes.toolbar}>
                        <IconButton onClick={this.handleDrawerClose}>
                            {theme.direction === 'rtl' ? <ChevronRightIcon/> : <Reply/>}
                        </IconButton>
                    </div>
                    <Divider/>
                    <MenuItem className={classes.menuItem}>
                        <ListItemIcon className={classes.icon}>
                            <ShoppingCart/>
                        </ListItemIcon>
                        {"Punto de Venta"}
                    </MenuItem>
                    <Divider/>
                    <MenuItem className={classes.menuItem}>
                        <ListItemIcon className={classes.icon}>
                            <InboxIcon/>
                        </ListItemIcon>
                        {"Cambio de Precio"}
                    </MenuItem>
                </Drawer>

                <main className={classes.content}>
                    <Home openMenu={this.state.open} addTicket={this.addTicket}/>
                    {/*<BrowserRouter>*/}
                    {/*<div>*/}
                    {/*<Route exact path="/" component={Home}/>*/}
                    {/*<Route exact path="/login" component={Login}/>*/}
                    {/*</div>*/}
                    {/*</BrowserRouter>*/}
                </main>


                <div className={classes.ticketDrawer}>
                    <Tabs
                        value={value}
                        onChange={this.handleChange}
                        indicatorColor="primary"
                        textColor="primary"
                        className={classes.bar}
                    >
                        <Tab label="Ticket 1" icon={<Receipt/>}/>
                        <Tab label="Ticket 2" icon={<Receipt/>}/>
                        <Tab label="Ticket 3" icon={<Receipt/>}/>
                    </Tabs>
                    {value === 0 &&
                    <Ticket numTicket={1} data={this.state.data0} handlePaymentTicket={this.handlePaymentTicket}
                            handleCancelTicket={this.handleCancelTicket} updateProduct={this.updateProduct}/>}
                    {value === 1 &&
                    <Ticket numTicket={2} data={this.state.data1} handlePaymentTicket={this.handlePaymentTicket}
                            handleCancelTicket={this.handleCancelTicket} updateProduct={this.updateProduct}/>}
                    {value === 2 &&
                    <Ticket numTicket={2} data={this.state.data2} handlePaymentTicket={this.handlePaymentTicket}
                            handleCancelTicket={this.handleCancelTicket} updateProduct={this.updateProduct}/>}
                </div>
                <Snackbar
                    anchorOrigin={{
                        vertical: 'bottom',
                        horizontal: 'left',
                    }}
                    open={this.state.payment}
                    autoHideDuration={3000}
                    onClose={this.handleClose}
                    ContentProps={{
                        'aria-describedby': 'message-id',
                    }}
                    message={<span id="message-id">Pago Realizado con exito!</span>}
                    action={[
                        <Button key="undo" color="secondary" size="small" onClick={() => this.handleClose('snackbar')}>
                            Cerrar
                        </Button>,
                        <IconButton
                            key="close"
                            aria-label="Close"
                            color="inherit"
                            className={classes.close}
                            onClick={() => this.handleClose('snackbar')}
                        >
                            <CloseIcon/>
                        </IconButton>,
                    ]}
                />
            </div>
        );
    }
}

MiniDrawer.propTypes = {
    classes: PropTypes.object.isRequired,
    theme: PropTypes.object.isRequired,
};

export default withStyles(styles, {withTheme: true})(MiniDrawer);