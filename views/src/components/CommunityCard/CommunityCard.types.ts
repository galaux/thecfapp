import { Community } from '../CommunityList/CommunityList.types';

export type CommunityCardProps = {
  community: Community;
  joinCommunity: (id: string) => void;
  navToCommunity: (id: string) => void;
};
