import React from 'react';
import ChatBot from 'react-simple-chatbot';
export default function App(){

  return(
    <ChatBot 
      floating="true"
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
        id:'em',
        message:'ABOUT TO WORK ON THIS SOON',
        end:true,
        },

      ]}
    />
    );
}
