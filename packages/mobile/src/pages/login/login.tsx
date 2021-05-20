import React, { FC, useState } from 'react';
import { RouteComponentProps } from '@reach/router'
import { Layout } from '../../components/Layout';
import { Header } from '../../components/Header';
import PageContent from '../../components/PageContent/PageContent';
import Input from '../../molecules/Input';
import { Aligns, ButtonType, ButtonVariant, Directions, InputType, Justifys, Size } from '../../types';
import Div from '../../common/Div';
import Link from '../../atoms/Link';
import { ButtonStyled, Separator, ButtonFacebook, Text } from './login.styled';

const LoginPage: FC<RouteComponentProps> = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const emailOnChange = (inputValue: string) => {
    setEmail(inputValue);
  }
  const passwordOnChange = (inputValue: string) => {
    setPassword(inputValue);
  }

  return(
    <Layout>
      <Header>Login</Header>
      <PageContent>
        <Div margin={ {bottom: Size.Xlarge} }>
          <Input
            label="Email Address"
            id="email"
            value={ email }
            onChange={ emailOnChange }
          />
        </Div>
        <Div margin={ {bottom: Size.Xsmall} }>
          <Input
            label="Password"
            id="password"
            value={ password }
            onChange={ passwordOnChange }
            type={ InputType.Password }
          />
        </Div>
        <Div justify={ Justifys.end }>
          <Link to={ '/' }>Forgot my password</Link>
        </Div>
        <Div margin={ { top: Size.Xxlarge, bottom: Size.Xlarge } } direction={ Directions.column } justify={ Justifys.center } align={ Aligns.center }>
          <ButtonStyled type={ ButtonType.button }>Sign in</ButtonStyled>
          <Separator>or</Separator>
          <ButtonFacebook variant={ ButtonVariant.Primary } to={ '/' }>Sign in with Facebook</ButtonFacebook>
        </Div>
        <Div direction={ Directions.column } justify={ Justifys.center } align={ Aligns.center }>
          <Text>Don’t have an account?</Text>
          <Link to={ '/register' }>Create my account</Link>
        </Div>
      </PageContent>
    </Layout>
  );
}

export default LoginPage;
