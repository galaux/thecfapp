export type Address = {
  street: string;
  city: string;
  zipCode: string;
  country: string;
};

export type Community = {
  id: string;
  name: string;
  memberCount: number;
  address: Address;
  isMember: boolean;
};

export type CommunityListProps = {
  communities: Community[];
  joinCommunity: (id: string) => void;
  navToCommunity: (id: string) => void;
};
