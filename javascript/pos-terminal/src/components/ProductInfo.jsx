import React from 'react';
import {connect} from 'react-redux';
import {compose} from 'redux'
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import {addToTicket} from "../actions/actions";
import classNames from 'classnames';

import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import FormControl from '@material-ui/core/FormControl';
import Input from '@material-ui/core/Input';
import InputAdornment from '@material-ui/core/InputAdornment';
import Button from '@material-ui/core/Button';
import ShoppingCart from '@material-ui/icons/ShoppingCart';
import Autorenew from '@material-ui/icons/Autorenew';
import CloseIcon from '@material-ui/icons/Close';
import InputLabel from '@material-ui/core/InputLabel';
import Switch from '@material-ui/core/Switch';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import ExpansionPanel from '@material-ui/core/ExpansionPanel';
import ExpansionPanelSummary from '@material-ui/core/ExpansionPanelSummary';
import ExpansionPanelDetails from '@material-ui/core/ExpansionPanelDetails';
import Typography from '@material-ui/core/Typography';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';

import AddIcon from '@material-ui/icons/Add';
import Remove from '@material-ui/icons/Remove';


const styles = theme => ({
    buttonDialogActions: {
        margin: theme.spacing.unit * 2,
        padding: '25px',
    },
    buttonUpdateAction: {
        margin: theme.spacing.unit,
        padding: '15px',
    },
    buttonDialog: {
        margin: theme.spacing.unit * 3,
    },
    margin: {
        margin: theme.spacing.unit,
    },
    withoutLabel: {
        marginTop: theme.spacing.unit * 4,
    },
    textField: {
        flexBasis: 30,
        width: theme.spacing.unit * 4.7,
    },
    input: {
        fontSize: '1.5rem',
    },
    dialogText: {
        fontSize: '1.4rem',
        textAlign: 'center',
        color: "rgba(247, 120, 107, 0.99)",
    },
    formHelperText: {
        fontSize: '1.2rem',
    },
    iconSmall: {
        fontSize: 20,
    },
    leftIcon: {
        marginRight: theme.spacing.unit,
    },
    textFieldKg: {
        flexBasis: 30,
        width: theme.spacing.unit * 24,
    },
    textFieldPrice: {
        flexBasis: 30,
        width: theme.spacing.unit * 20,
    },
    priceDialog: {
        // color: "rgba(0, 0, 0, 0.99)",
        marginLeft: '20px',
    },
    priceTotal: {
        marginTop: theme.spacing.unit,
        fontSize: '1.4rem',
        textAlign: 'center',
        color: "rgba(0, 0, 0, 0.59)",
    },
    productDetails: {
        marginLeft: '40px',
    },
    iOSSwitchBase: {
        '&$iOSChecked': {
            color: theme.palette.common.white,
            '& + $iOSBar': {
                backgroundColor: '#52d869',
            },
        },
        transition: theme.transitions.create('transform', {
            duration: theme.transitions.duration.shortest,
            easing: theme.transitions.easing.sharp,
        }),
    },
    iOSChecked: {
        transform: 'translateX(15px)',
        '& + $iOSBar': {
            opacity: 1,
            border: 'none',
        },
    },
    iOSBar: {
        borderRadius: 13,
        width: 42,
        height: 26,
        marginTop: -13,
        marginLeft: -21,
        border: 'solid 1px',
        borderColor: theme.palette.grey[400],
        backgroundColor: '#00BCD4',
        opacity: 1,
        transition: theme.transitions.create(['background-color', 'border']),
    },
    iOSIcon: {
        width: 24,
        height: 24,
    },
    iOSIconChecked: {
        boxShadow: theme.shadows[1],
    },
    column: {
        flexBasis: '50%',
    },
});


class ProductInfo extends React.Component {
    constructor(props) {
        super(props);
        this.state.addTicket = this.props.addTicket;
        this.state.closeDialog = this.props.closeDialog;
        this.state.product = this.props.product;
        this.state.precio = this.props.product.precio;

        if (this.props.showCantidad) {
            this.state.product.cantidad = this.state.product.total;
        }
    }

    state = {
        openDialog: true,
        grams: true,
        // precio:0,
    };

    handleSwitch = name => event => {
        this.setState({[name]: event.target.checked});
    };


    handleChange = product => event => {
        if (Number(event.target.value)) {
            product.cantidad = Number(event.target.value).valueOf();
        } else {
            product.cantidad = 0;
        }
        this.setState({product: product});
    };


    handleChangeGrams = product => event => {
        product.cantidad = event.target.value;
        this.setState({product: product});
    };

    handleChangePesos = product => event => {
        product.cantidad = event.target.value / product.precio;
        this.setState({product: product});
    };

    handleChangePrecio = () => event => {
        this.setState({precio: event.target.value});
    };

    addOne = (product) => {
        let newproduct = product;
        newproduct.cantidad++;
        this.setState({product: newproduct});
    };

    subOne = (product) => {
        let newproduct = product;
        newproduct.cantidad--;
        this.setState({product: newproduct});
    };

    addToTicket = (product) => {
        if (!product.piezas) {
            product.cantidad = parseFloat(product.cantidad).toFixed(4);
        }
        this.state.addTicket(product);
        let newproduct = product;
        newproduct.cantidad = 1;
        this.state.closeDialog();
        this.setState({openDialog: false, product: newproduct});
    };

    updatePrice = (product) => {
        product.precio = parseFloat(this.state.precio).toFixed(2);
        this.setState({openDialog: false, product: product});
    };

    closeDialog = () => {
        this.state.closeDialog();

    };


    render() {
        const {classes} = this.props;
        return (
            <Dialog
                open={true}
                onClose={this.closeDialog}
                aria-labelledby="responsive-dialog-title"
            >
                <DialogTitle
                    id="responsive-dialog-title">{this.props.showCantidad ? "Actualizar Ticket" : "Agregar a Ticket"}</DialogTitle>
                <DialogContent>
                    <ExpansionPanel>
                        <ExpansionPanelSummary className={classes.dialogText} expandIcon={<ExpandMoreIcon/>}>
                            <div className={classes.column}>
                                <Typography className={classes.dialogText}>
                                    {this.state.product.producto}
                                </Typography>
                            </div>
                            <div className={classes.column}>
                                <span className={classes.productDetails}> $ {this.state.product.precio}</span>
                            </div>
                        </ExpansionPanelSummary>
                        <ExpansionPanelDetails>
                            <div className={classes.column}>
                                <FormControl
                                    aria-describedby="weight-helper-text"
                                >
                                    <InputLabel htmlFor="adornment-amount" className={classes.formHelperText}>Actualizar
                                        Precio</InputLabel>
                                    <Input
                                        id="adornment-weight"
                                        type="number"
                                        value={this.state.precio}
                                        onChange={this.handleChangePrecio()}
                                        className={classes.textFieldPrice}
                                        startAdornment={<InputAdornment position="start">$</InputAdornment>}
                                    />

                                </FormControl>
                            </div>
                            <div className={classes.column}>
                                <Button variant="contained" size="large"
                                        onClick={() => this.updatePrice(this.state.product)}
                                        color="primary" autoFocus className={classes.buttonUpdateAction}>
                                    <Autorenew className={classNames(classes.leftIcon, classes.iconSmall)}/>
                                    {"Actualizar"}
                                </Button>
                            </div>
                        </ExpansionPanelDetails>
                    </ExpansionPanel>
                    <DialogContentText className={classes.priceTotal}>
                        Total: <span
                        className={classes.priceDialog}>$ {(this.state.product.precio * this.state.product.cantidad).toFixed(2)}</span>
                    </DialogContentText>

                    {this.state.product.piezas ? (
                        <DialogContentText className={classes.dialogText}>
                            <Button variant="fab" disabled={this.state.product.cantidad <= 0}
                                    onClick={() => this.subOne(this.state.product)} color="primary"
                                    className={classes.buttonDialog}>
                                <Remove/>
                            </Button>
                            <FormControl
                                className={classNames(classes.margin, classes.withoutLabel, classes.textField)}
                                aria-describedby="weight-helper-text"
                            >
                                <InputLabel htmlFor="adornment-amount"
                                            className={classes.formHelperText}>Cantidad</InputLabel>
                                <Input
                                    id="adornment-weight"
                                    type="number"
                                    value={this.state.showCantidad ? this.state.product.total : this.state.product.cantidad}
                                    onChange={this.handleChange(this.state.product)}
                                    className={classes.input}
                                />
                            </FormControl>
                            <Button variant="fab" onClick={() => this.addOne(this.state.product)} color="primary"
                                    className={classes.buttonDialog}>
                                <AddIcon/>
                            </Button>
                        </DialogContentText>
                    ) : (
                        <div>
                            <FormControlLabel
                                control={
                                    <Switch
                                        classes={{
                                            switchBase: classes.iOSSwitchBase,
                                            bar: classes.iOSBar,
                                            icon: classes.iOSIcon,
                                            iconChecked: classes.iOSIconChecked,
                                            checked: classes.iOSChecked,
                                        }}
                                        disableRipple
                                        checked={this.state.grams}
                                        onChange={this.handleSwitch('grams')}
                                        value="grams"
                                    />
                                }
                                label={this.state.grams ? "Cambiar a $" : "Cambiar a Kg"}
                            />

                            {this.state.grams ? (
                                <DialogContentText className={classes.dialogText}>
                                    <FormControl
                                        className={classNames(classes.withoutLabel, classes.textFieldKg)}
                                        aria-describedby="weight-helper-text"
                                    >
                                        <InputLabel htmlFor="adornment-amount" className={classes.formHelperText}>Agregar
                                            Peso</InputLabel>
                                        <Input
                                            id="adornment-weight"
                                            type="number"
                                            value={this.state.showCantidad ? this.state.product.total : this.state.product.cantidad}
                                            onChange={this.handleChangeGrams(this.state.product)}
                                            className={classes.textFieldKg}
                                            endAdornment={<InputAdornment position="end">Kg</InputAdornment>}
                                        />
                                    </FormControl>
                                </DialogContentText>
                            ) : (

                                <DialogContentText className={classes.dialogText}>
                                    <FormControl
                                        className={classNames(classes.withoutLabel, classes.textFieldKg)}
                                        aria-describedby="weight-helper-text"
                                    >
                                        <InputLabel htmlFor="adornment-amount" className={classes.formHelperText}>Agregar
                                            Pesos $</InputLabel>
                                        <Input
                                            id="adornment-weight"
                                            type="number"
                                            value={this.state.showCantidad ? (this.state.product.total * this.state.product.precio) : (this.state.product.cantidad * this.state.product.precio)}
                                            onChange={this.handleChangePesos(this.state.product)}
                                            className={classes.textFieldKg}
                                            startAdornment={<InputAdornment position="start">$</InputAdornment>}
                                        />
                                    </FormControl>
                                </DialogContentText>
                            )}
                        </div>
                    )}
                </DialogContent>
                <DialogActions>
                    <Button variant="contained" size="large" onClick={() => this.closeDialog()} color="secondary"
                            className={classes.buttonDialogActions}>
                        <CloseIcon className={classNames(classes.leftIcon, classes.iconSmall)}/>
                        Cancelar
                    </Button>
                    <Button variant="contained" size="large" onClick={() => this.addToTicket(this.state.product)}
                            color="primary" autoFocus className={classes.buttonDialogActions}>
                        <ShoppingCart className={classNames(classes.leftIcon, classes.iconSmall)}/>
                        {this.props.showCantidad ? "Actualizar" : "Agregar a Ticket"}
                    </Button>
                </DialogActions>
            </Dialog>
        );
    }
};


function mapStateToProps({products}) {
    return {
        products
    }
}

ProductInfo.propTypes = {
    classes: PropTypes.object.isRequired,
    theme: PropTypes.object.isRequired,
};

export default compose(
    withStyles(styles, {withTheme: true}),
    connect(mapStateToProps, {
        addToTicket,
    }),
)(ProductInfo);

