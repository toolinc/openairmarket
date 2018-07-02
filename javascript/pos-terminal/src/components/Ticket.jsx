import React from 'react';
import {connect} from 'react-redux';
import {compose} from 'redux'
import PropTypes from 'prop-types';
import classNames from 'classnames';
import {addToTicket} from '../actions/actions'


import {createMuiTheme, MuiThemeProvider, withStyles} from "@material-ui/core/styles/index";
import green from "@material-ui/core/colors/lime";
import pink from "@material-ui/core/colors/pink";

import GridList from '@material-ui/core/GridList';
import GridListTile from '@material-ui/core/GridListTile';
import ListSubheader from '@material-ui/core/ListSubheader';
import Button from '@material-ui/core/Button';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import Drawer from '@material-ui/core/Drawer';
import Edit from '@material-ui/icons/Edit';
import CloseIcon from '@material-ui/icons/Close';
import ChevronLeftIcon from '@material-ui/icons/Undo';

import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';

import Payment from '@material-ui/icons/Payment';
import Delete from '@material-ui/icons/Delete';
import ProductInfo from './ProductInfo';


const drawerWidthTicket = 550;
const styles = theme => ({
    root: {
        // height: '400px',
    },
    rootTicket: {
        display: 'flex',
        flexWrap: 'wrap',
        justifyContent: 'space-around',
        overflow: 'hidden',
        backgroundColor: theme.palette.background.paper,
        // height: '500px',
    },
    textField: {
        marginLeft: theme.spacing.unit,
        marginRight: theme.spacing.unit,
        width: 200,
    },
    tableWrapper: {
        overflowX: 'auto',
        marginBottom: theme.spacing.unit * 3,
    },
    table: {
        minWidth: 100,
    },
    rootPaper: {
        height: "100%",
        width: '100%',
        overflow: 'auto',
    },
    drawerPaperTicket: {
        position: 'relative',
        whiteSpace: 'nowrap',
        width: drawerWidthTicket,
    },
    button: {
        margin: theme.spacing.unit * 3,
        padding: theme.spacing.unit * 3,
    },
    buttonDelte: {
        marginTop: theme.spacing.unit * 0.5,
        marginBottom: theme.spacing.unit * 0.5,
        marginLeft: theme.spacing.unit * 0.5,
    },
    dialogText: {
        fontSize: '1.5rem',
        textAlign: 'center',
    },
});

const themePagar = createMuiTheme({
    palette: {
        primary: green,
    },
});

const themeCancel = createMuiTheme({
    palette: {
        primary: pink,
    },
});


let counter = 0;

function createData(cantidad, producto, precio) {
    counter += 1;
    return {id: counter, cantidad, producto, precio};
}


class Ticket extends React.Component {
    constructor(props) {
        super(props);
        this.state.handleCancelTicket = this.props.handleCancelTicket;
        this.state.handlePaymentTicket = this.props.handlePaymentTicket;
        this.state.updateProduct = this.props.updateProduct;
    }

    state = {
        open: false,
        anchorEl: null,
        openPayDialog: false,
        openCancelDialog: false,
        openProductDialog: false,
        product: null,
    };


    handleClickOpenDialog = (dialog) => {
        this.setState({[dialog]: true});
    };

    handleCloseDialog = (dialog, clear) => {
        this.setState({[dialog]: false});
        if(dialog == 'openCancelDialog' && clear){
            this.state.handleCancelTicket();
        } else if(dialog == 'openPayDialog' && clear){
            this.state.handlePaymentTicket();
        }
    };

    openProductDialog = (product) => {
        this.setState({openProductDialog: true, product: product});
    };

    closeProductDialog = () => {
        this.setState({openProductDialog: false});
    };


    render() {
        const {classes, data} = this.props;
        let tempTotal = 0;

        return (
            <div className={classes.root}>
                <Drawer
                    anchor="right"
                    variant="permanent"
                    classes={{
                        paper: classNames(classes.drawerPaperTicket),
                    }}
                    open={true}
                >
                    <div className={classes.rootTicket}>
                        <GridList cellHeight={100} spacing={1} className={classes.gridList}>
                            <GridListTile key="Subheader" cols={2} style={{height: 'auto'}}>
                                <Table className={classes.table}>
                                    <TableHead>
                                        <TableRow>
                                            <TableCell style={{fontSize: '1.1rem'}}
                                                       numeric>Cantidad</TableCell>
                                            <TableCell
                                                style={{fontSize: '1.1rem', textAlign: 'left'}}>Producto</TableCell>
                                            <TableCell style={{fontSize: '1.1rem'}} numeric>Precio</TableCell>
                                        </TableRow>
                                    </TableHead>
                                </Table>
                            </GridListTile>

                            <GridListTile cols={2} rows={4}>
                                <Paper className={classes.rootPaper}>
                                    <div className={classes.tableWrapper}>
                                        <Table className={classes.table}>
                                            <TableBody>
                                                {data.map((product, key) => {
                                                    tempTotal += (product.precio * product.total);
                                                    return (
                                                        <TableRow key={key}>
                                                            <TableCell padding="checkbox">
                                                                <Button variant="fab" mini color="primary"
                                                                        onClick={() => this.openProductDialog(product)}
                                                                        className={classNames(classes.buttonDelte)}>
                                                                    <Edit/>
                                                                </Button>
                                                            </TableCell>
                                                            <TableCell style={{fontSize: '1rem'}}
                                                                       scope="row">
                                                                <span style={{
                                                                    whiteSpace: 'nowrap',
                                                                    overflow: 'hidden',
                                                                    width: '50px',
                                                                    display: 'block',
                                                                    fontSize: '1.1rem'
                                                                }}>
                                                                    {parseFloat(product.total).toFixed(2)}
                                                                </span>
                                                            </TableCell>
                                                            <TableCell>
                                                                    <span style={{
                                                                        whiteSpace: 'nowrap',
                                                                        overflow: 'hidden',
                                                                        width: '170px',
                                                                        display: 'block',
                                                                        fontSize: '1.2rem'
                                                                    }}>
                                                                        {product.producto}
                                                                    </span>
                                                            </TableCell>
                                                            <TableCell style={{fontSize: '1.2rem'}} numeric>
                                                                ${parseFloat(product.precio * product.total).toFixed(2)}
                                                            </TableCell>
                                                        </TableRow>
                                                    );
                                                })}
                                            </TableBody>
                                        </Table>
                                    </div>
                                </Paper>
                            </GridListTile>
                            <GridListTile cols={2} style={{height: 'auto', textAlign: 'right'}}>
                                <ListSubheader component="div" style={{fontSize: '1.8rem'}}>Total
                                    ${parseFloat(tempTotal).toFixed(2)}</ListSubheader>
                            </GridListTile>
                            <GridListTile cols={1} rows={2}>
                                <MuiThemeProvider theme={themeCancel}>
                                    <Button variant="contained" color="primary" className={classes.button}
                                            onClick={()=>this.handleClickOpenDialog('openCancelDialog')}>
                                        <Delete className={classNames(classes.leftIcon, classes.iconSmall)}/>
                                        Borrar Ticket
                                    </Button>
                                </MuiThemeProvider>
                            </GridListTile>
                            <GridListTile cols={1} rows={2}>
                                <MuiThemeProvider theme={themePagar}>
                                    <Button variant="contained" color="primary" className={classes.button}
                                            onClick={()=>this.handleClickOpenDialog('openPayDialog')}>
                                        <Payment className={classNames(classes.leftIcon, classes.iconSmall)}/>
                                        Pagar Ticket
                                    </Button>
                                </MuiThemeProvider>
                            </GridListTile>
                        </GridList>
                    </div>
                </Drawer>

                {this.state.openProductDialog && (
                    <ProductInfo showCantidad={true} addTicket={this.state.updateProduct} product={this.state.product}
                                 closeDialog={this.closeProductDialog}/>)}
                <Dialog
                    open={this.state.openCancelDialog}
                    onClose={this.handleCloseDialog}
                    aria-labelledby="responsive-dialog-title"
                >
                    <DialogTitle id="responsive-dialog-title">{" Borrar Ticket"}</DialogTitle>
                    <DialogContent>
                        <DialogContentText className={classes.dialogText}>
                            Deseas borrar el ticket?
                        </DialogContentText>
                    </DialogContent>
                    <DialogActions>
                        <Button variant="contained" autoFocus color="primary" className={classes.button}
                                onClick={()=>this.handleCloseDialog('openCancelDialog', true)}>
                            <Delete className={classNames(classes.leftIcon, classes.iconSmall)}/>
                            Borrar Ticket
                        </Button>
                        <Button variant="contained" color="secondary" className={classes.button}
                                onClick={()=>this.handleCloseDialog('openCancelDialog')}>
                            <CloseIcon className={classNames(classes.leftIcon, classes.iconSmall)}/>
                            Cancelar
                        </Button>
                    </DialogActions>
                </Dialog>
                <Dialog
                    open={this.state.openPayDialog}
                    onClose={this.handleCloseDialog}
                    aria-labelledby="responsive-dialog-title"
                >
                    <DialogTitle id="responsive-dialog-title">{" Finalizar Venta"}</DialogTitle>
                    <DialogContent>
                        <DialogContentText className={classes.dialogText}>
                            Total de la venta ${tempTotal}
                        </DialogContentText>
                    </DialogContent>
                    <DialogActions>
                        <Button variant="contained" color="secondary" className={classes.button}
                                onClick={()=>this.handleCloseDialog('openPayDialog')}>
                            <ChevronLeftIcon className={classNames(classes.leftIcon, classes.iconSmall)}/>
                            Regresar
                        </Button>
                        <Button variant="contained" color="primary" className={classes.button}
                                onClick={()=>this.handleCloseDialog('openPayDialog', true)}>
                            <Payment className={classNames(classes.leftIcon, classes.iconSmall)}/>
                            Pagar Ticket
                        </Button>
                    </DialogActions>
                </Dialog>
            </div>
        );
    }
}

function mapStateToProps({products}) {
    return {
        products
    }
}

Ticket.propTypes = {
    classes: PropTypes.object.isRequired,
    theme: PropTypes.object.isRequired,
};

export default compose(
    withStyles(styles, {withTheme: true}),
    connect(mapStateToProps, {
        addToTicket,
    }),
)(Ticket);