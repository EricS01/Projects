module.exports = class Friendship {
    id = null;
    user1Id = null;
    user2Id = null;
    status = null;

    constructor(data) {
        this.id = data.friendship_id;
        this.user1Id = data.user1_id;
        this.user2Id = data.user2_id;
        this.status = data.status;
    }
};
