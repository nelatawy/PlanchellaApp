export enum EventSize{
  SMALL = "small",
  MID = "medium",
  LARGE = "large"
}

export enum EventType {
  HACKATHON = "hackathon",
  CONTEST = "contest",
  RELEASE = "release",
  OTHER = "other"
}

export enum VoteType {
  UPVOTE = "upvote",
  DOWNVOTE = "downvote",
  NONE = "none"
}

export enum NotificationType {
  EVENT_REMINDER = "event_reminder",
  NEW_COMMENT = "new_comment",
  UPVOTE = "upvote",
  COMMUNITY_INVITE = "community_invite"
}

export enum CommunityRole {
  Creator = "creator",
  TOP_CONTRIBUTOR = "top_contributor",
  MEMBER = "member",
  GUEST = "guest"
}