import { IWorkout } from './workout.d';

export interface IWod {
  id: string;
  name: string;
  workout: IWorkout;
}
