import React, { FC, useState } from 'react';
import { Search } from 'react-feather';
import { useTranslation } from 'react-i18next';
import Button from '../../atoms/Button';
import Label from '../../atoms/Label';
import Div from '../../common/Div';
import InputGroup from '../../common/InputGroup';
import CommunityList from '../../components/CommunityList';
import Header from '../../components/Header';
import PageContent from '../../components/PageContent';
import { communitiesMock } from '../../mockData/mockData';
import Input from '../../molecules/Input';
import { ButtonType, Size } from '../../types';
import { FindCommunityProps } from './FindCommunity.types';

const FindCommunity: FC<FindCommunityProps> = () => {
  const { t } = useTranslation();
  const [name, setName] = useState('');

  const onClickFind = () => {
    if (name) {
      // findCommunities(name);
    }
  };

  const searchOnChange = (inputValue: string) => {
    setName(inputValue);
  };
  return (
    <>
      <Header title={ t('findCommunity_title') } subtitle={ t('findCommunity_subtitle') } />
      <PageContent>
        <Div margin={ { bottom: Size.Xlarge } }>
          <Label htmlFor="search">{ t('findCommunity_searchInputLabel') }</Label>
          <InputGroup>
            <Input id="search" value={ name } onChange={ searchOnChange } />
            <Button type={ ButtonType.button } onClick={ onClickFind }>
              <Search size="14" />
            </Button>
          </InputGroup>
        </Div>
        <CommunityList communities={ communitiesMock } />
      </PageContent>
    </>
  );
};

export default FindCommunity;
