import { IMovement } from './movement';

export interface IWod {
  id: string;
  name: string;
  desc: string;
  movements: IMovement[];
}
