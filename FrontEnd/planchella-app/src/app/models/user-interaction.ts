import { VoteType } from "./Enums"

export interface UserVote {
  id: string,
  userId: string,
  eventId: string,
  voteType: VoteType,
  timestamp: Date
}

export interface EventStarred {
  id: string,
  userId: string,
  eventId: string,
  createdAt: Date
}