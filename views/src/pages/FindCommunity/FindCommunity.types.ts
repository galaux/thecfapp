import { Community } from '../../components/CommunityList/CommunityList.types';
import { PageProps } from '../pages.types';

export type FindCommunityProps = PageProps & {
  findCommunities: (name: string) => void;
  communities: Community[];
  joinCommunity: (id: string) => void;
  navToCommunity: (id: string) => void;
};
