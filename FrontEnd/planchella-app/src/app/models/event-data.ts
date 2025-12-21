import { EventSize, EventType } from './Enums';
import { EventAttachment } from './event-attachment';
import { EventAuthorData } from './event-author-data';


// export interface EventData {
//   id?: string,
//   eventType: EventType,
//   eventSize: EventSize,
//   authorData: EventAuthorData,
//   title: string,
//   description: string,
//   creationDate: Date | string | number,
//   upVotesCount?: number,
//   downVotesCount?: number,
//   eventStartDate: Date | string | number,
//   eventEndDate: Date | string | number,
//   attachments?: EventAttachment[]
// }

export interface EventData {
  id?: number,
  eventType: EventType,
  eventSize: EventSize,
  authorId: number,
  communityId: number,
  title: string,
  description: string,
  creationDate: Date | string | number,
  upvoteCount?: number,
  downvoteCount?: number,
  eventStartDate: Date | string | number,
  eventEndDate: Date | string | number,
  isUpvoted?: boolean,
  isDownVoted?: boolean,
  isStarred?: boolean,
  attachments?: EventAttachment[]
}