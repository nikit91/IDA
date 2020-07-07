import React from 'react';
import ChatBot from 'react-simple-chatbot';
import PropTypes from 'prop-types';
import { makeStyles } from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';


const useStyles = makeStyles((theme) => ({
    root: {
      '& > *': {
        margin: theme.spacing(1),
      },
    },
    input: {
      display: 'none',
    },
  }));

function Upload(){
   
    const classes = useStyles();
    return (
      <div className={classes.root}>
        <input
          accept="file_extention/*"
          className={classes.input}
          id="contained-button-file"
          multiple
          type="file"
          directory="" webkitdirectory=""
        />
        <label htmlFor="contained-button-file">
          <Button variant="contained" color="default" component="span" >
            Upload
          </Button>
        </label>
        </div>
    );
}
Upload.propTypes = {
    steps: PropTypes.object,
  };
  
  Upload.defaultProps = {
    steps: undefined,
  };
export default function App(){

  return(
    <ChatBot 
      floating="true"
      headerTitle = 'IDA'
      steps={[
        {
          id: 'Greet',
          message: 'Hello, Welcome to IDA',
          trigger: 'intro',
        },
        {
          id: 'intro',
          message: 'I am your service agent Bot, What would you like to do ?',
          trigger : 'option1',
        },
        {
          id: 'option1',
          options: [
            { value: 1, label: ' Know more about chatbot', trigger: 'about' },
            { value: 2, label: ' Loading a dataset', trigger: 'load' },
            { value: 3, label: ' Access the existing dataset', trigger: 'reload' },
          ],
        },
        {
          id: 'about',
          message:'The service Bot will help analyze the dataset, give suggestion and  help users view visualisations',
          trigger :'option1'
        },
        {
          id: 'load',
          message:'Please upload the folder',
          trigger :'em'
        },
        {
          id:'reload',
          message:'Here further we will access the existing dataset',
          trigger:'em'
        },
        {
            id: 'em',
            component: <Upload />,
            asMessage: true,
            trigger: 'option1',
        },

      ]}
    />
    );
}
