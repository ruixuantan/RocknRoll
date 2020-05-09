import React from "react";
import Roller from "./component/roller/Roller";
import Test from "./Test";
import { Header } from 'semantic-ui-react';


const App: React.FC = () => {
  return (
      <div>
        <Header as='h1' textAlign='center'>
          Rock n Roll
        </Header>
        <Roller />
      </div>
    );
}

export default App;
