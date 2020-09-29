import { Community } from './community';

export type User = {
  id: string;
  name: string;
  communities: Community[];
};
