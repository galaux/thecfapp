import { IWod } from './wod';

export interface ICommunity {
  id: string;
  name: string;
  wods: IWod[];
}
