import {CommunityData} from './community-data';

export interface CommunityCardData {
  id : string,
  communityData ?: CommunityData,
  currentlySelected?: boolean
}
