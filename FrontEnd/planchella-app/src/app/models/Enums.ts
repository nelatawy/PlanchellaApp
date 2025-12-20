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


export enum MimeType {
  // Document and Text Types
  TEXT_PLAIN = "text/plain",
  TEXT_CSV = "text/csv",
  TEXT_HTML = "text/html",
  TEXT_CSS = "text/css",
  APPLICATION_PDF = "application/pdf",
  APPLICATION_MSWORD = "application/msword",
  APPLICATION_DOCX = "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
  APPLICATION_MSEXCEL = "application/vnd.ms-excel",
  APPLICATION_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
  APPLICATION_MSPOWERPOINT = "application/vnd.ms-powerpoint",
  APPLICATION_PPTX = "application/vnd.openxmlformats-officedocument.presentationml.presentation",
  
  // Image Types
  IMAGE_JPEG = "image/jpeg",
  IMAGE_PNG = "image/png",
  IMAGE_GIF = "image/gif",
  IMAGE_WEBP = "image/webp",
  IMAGE_SVG = "image/svg+xml",
  IMAGE_TIFF = "image/tiff",
  
  // Compressed and Archive Types
  APPLICATION_ZIP = "application/zip",
  APPLICATION_RAR = "application/x-rar-compressed",
  APPLICATION_7Z = "application/x-7z-compressed",
  APPLICATION_TAR = "application/x-tar",
  APPLICATION_GZIP = "application/gzip",
  
  // Audio and Video Types
  AUDIO_MPEG = "audio/mpeg",
  AUDIO_WAV = "audio/wav",
  AUDIO_OGG = "audio/ogg",
  VIDEO_MP4 = "video/mp4",
  VIDEO_QUICKTIME = "video/quicktime",
  VIDEO_AVI = "video/x-msvideo",
  
  // Application and Data Types
  APPLICATION_JSON = "application/json",
  APPLICATION_XML = "application/xml",
  APPLICATION_OCTET_STREAM = "application/octet-stream"
}
