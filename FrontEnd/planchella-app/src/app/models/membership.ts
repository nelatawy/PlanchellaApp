import { CommunityRole } from "./Enums";

export interface Mempership {
    id: string,
    userId: string,
    communityId: string,
    role: CommunityRole,
    joinedAt: Date
}