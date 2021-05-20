import React, { FC, useState } from 'react';
import { RouteComponentProps } from '@reach/router';
import Div from '../../common/Div';
import { Header } from '../../components/Header';
import { Layout } from '../../components/Layout';
import PageContent from '../../components/PageContent';
import { Aligns, ButtonType, ButtonVariant, Directions, InputType, Justifys, Size } from '../../types';
import Input from '../../molecules/Input';
import { ButtonStyled, ButtonFacebook, Separator, Text } from '../login/login.styled';
import Link from '../../atoms/Link';

const RegisterPage: FC<RouteComponentProps> = () => {
  const [firstname, setFirstname] = useState('');
  const [lastname, setLastname] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const firstnameOnChange = (inputValue: string) => {
    setFirstname(inputValue);
  }
  const lastnameOnChange = (inputValue: string) => {
    setLastname(inputValue);
  }
  const emailOnChange = (inputValue: string) => {
    setEmail(inputValue);
  }
  const passwordOnChange = (inputValue: string) => {
    setPassword(inputValue);
  }
  return (
    <Layout>
      <Header>Register</Header>
      <PageContent>
        <Div margin={ {bottom: Size.Xlarge} }>
          <Input
            label="Firstname"
            id="firstname"
            value={ firstname }
            onChange={ firstnameOnChange }
          />
        </Div>
        <Div margin={ {bottom: Size.Xlarge} }>
          <Input
            label="Lastname"
            id="lastname"
            value={ lastname }
            onChange={ lastnameOnChange }
          />
        </Div>
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
        <Div margin={ {bottom: Size.Xxlarge} }>
          <Input
            label="Password"
            id="password"
            value={ password }
            onChange={ passwordOnChange }
            type={ InputType.Password }
          />
        </Div>
        <Div margin={ {bottom: Size.Xlarge} } direction={ Directions.column } justify={ Justifys.center } align={ Aligns.center }>
          <ButtonStyled type={ ButtonType.button }>Register</ButtonStyled>
          <Separator>or</Separator>
          <ButtonFacebook variant={ ButtonVariant.Primary } to={ '/' }>Register with Facebook</ButtonFacebook>
        </Div>
        <Div direction={ Directions.column } justify={ Justifys.center } align={ Aligns.center }>
          <Text>Already have an account?</Text>
          <Link to={ '/login' }>Log in</Link>
        </Div>
      </PageContent>
    </Layout>
  );
}

export default RegisterPage;
