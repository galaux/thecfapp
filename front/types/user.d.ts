import { ICommunity } from './community';

export interface IUser {
  id: string;
  name: string;
  communities: ICommunity[];
}
