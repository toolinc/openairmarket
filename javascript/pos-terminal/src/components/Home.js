import React from 'react';
import PropTypes from 'prop-types';
import { withStyles, MuiThemeProvider } from '@material-ui/core/styles';
import {createMuiTheme} from "@material-ui/core/styles/index";

import GridList from '@material-ui/core/GridList';
import GridListTile from '@material-ui/core/GridListTile';
import Button from '@material-ui/core/Button';

import red from '@material-ui/core/colors/red';
import yellow from '@material-ui/core/colors/yellow';
import green from "@material-ui/core/colors/lime";
import blue from "@material-ui/core/colors/blue";
import orange from "@material-ui/core/colors/orange";
import amber from "@material-ui/core/colors/amber";

import data from '../data';
import ProductInfo from './ProductInfo';

const isMobile = window.innerWidth <= 770;
const themePan = createMuiTheme({
    palette: {
        primary: yellow,
    },
});

const themeJamon = createMuiTheme({
    palette: {
        primary: red,
    },
});

const themeCigarros = createMuiTheme({
    palette: {
        primary: blue,
    },
});

const themeVerdura = createMuiTheme({
    palette: {
        primary: green,
    },
});

const themeFruta = createMuiTheme({
    palette: {
        primary: orange,
    },
});

const themeQueso = createMuiTheme({
    palette: {
        primary: amber,
    },
});


const styles = theme => ({
    rootGrid: {
        display: 'flex',
        flexWrap: 'wrap',
        justifyContent: 'space-around',
        overflow: 'hidden',
        backgroundColor: theme.palette.background.paper,
        height: '100%',
        // paddingBottom: '25px',
        width: '100%',
    },
    rootGridMobile: {
        display: 'flex',
        flexWrap: 'wrap',
        justifyContent: 'space-around',
        overflow: 'hidden',
        backgroundColor: theme.palette.background.paper,
        height: '690px',
        // paddingBottom: '25px',
        width: '100%',
    },
    root: {
        flexGrow: 1,
        backgroundColor: theme.palette.background.default,
    },
    gridList: {
        width: '100%',
        height: "100%",
    },
    chipColor: {
        background: 'linear-gradient(45deg, #FE6B8B 30%, #FF8E53 90%)',
        borderRadius: 3,
        border: 0,
        color: 'white',
        height: 48,
        padding: '0 30px',
        boxShadow: '0 3px 5px 2px rgba(255, 105, 135, .3)',
    },
    label: {
        textTransform: 'capitalize',
    },
    button: {
        margin: theme.spacing.unit * 2,
        padding: '17px',
        width: theme.spacing.unit * 4,
    },
    small:{
        width: '100%',
    }

});


class Home extends React.Component {
    constructor(props) {
        super(props);
        this.state.addTicket = this.props.addTicket;
    }
    state = {
        value: 0,
        data:data,
        openDialog: false,
        product: {
            id: 0,
            cantidad: 0,
            precio: 0,
            producto: "",
            color: "",
            categoria:"",
        },
    };

    handleClickOpenDialog = (product) => {
        this.setState({openDialog: true, product:product});
    };

    closeDialog = () => {
        this.setState({openDialog: false});
    };

    choseColor = (categoria) =>{
        switch (categoria){
            case 'pan':
                return themePan;
                break;
            case 'jamon':
                return themeJamon;
                break;
            case 'queso':
                return themeQueso;
                break;
            case 'cigarro':
                return themeCigarros;
                break;
            case 'fruta':
                return themeFruta;
                break;
            case 'verdura':
                return themeVerdura;
                break;
            default:
                return themeCigarros;
                break;
        }
    };


    render() {
        const { classes } = this.props;
        const { data } = this.state;

        return (
            <div className={isMobile ? classes.rootGridMobile : classes.rootGrid}>
                <div className={classes.rootGrid}>
                    <GridList cellHeight={90} cols={this.props.openMenu ? 2 : 3} className={classes.gridList}>

                        {data.map((tile,key) => (
                            <GridListTile key={key} className={classes.small}>
                                <MuiThemeProvider theme={this.choseColor(tile.categoria)}>
                                    <Button variant="contained" size="large" color="primary" className={classes.button}
                                            onClick={()=>this.handleClickOpenDialog(tile)}>
                                        {tile.producto}
                                    </Button>


                                </MuiThemeProvider>
                            </GridListTile>
                        ))}
                    </GridList>
                </div>

                {this.state.openDialog && (<ProductInfo addTicket={this.state.addTicket} product={this.state.product}  closeDialog={this.closeDialog}/>)}

            </div>
        );
    }
}

Home.propTypes = {
    classes: PropTypes.object.isRequired,
    theme: PropTypes.object.isRequired,
};

export default withStyles(styles, {withTheme: true})(Home);