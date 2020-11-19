import * as React from 'react';
import { useRouter } from 'next/router';

import UserCommunities from '..';

const UserCommunity: React.FC = () => {
  const router = useRouter();
  const { community_id } = router.query;

  return <UserCommunities currentCommunityId={community_id as string} />;
};

export default UserCommunity;
