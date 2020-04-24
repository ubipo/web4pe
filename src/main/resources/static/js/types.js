class User {
  uuid
  username
  name
  lastName
  status

  constructor(uuid, username, name, lastName, status) {
    this.uuid = uuid
    this.username = username
    this.name = name
    this.lastName = lastName
    this.status = status
  }

  static fromObj(obj) {
    return new User(obj.uuid, obj.username, obj.name, obj.lastName, obj.status)
  }
}


class Friendship {
  requester
  requestee

  constructor(requester, requestee) {
    this.requester = requester;
    this.requestee = requestee;
  }

  /**
   * Gets the other user in the friendship
   * @param me {User}
   * @returns {User}
   */
  getOther(me) {
    if (me.uuid === this.requester.uuid) {
      return this.requestee
    } else if (me.uuid === this.requestee.uuid) {
      return this.requester
    } else {
      throw new Error("Neither requester nor requestee have the specified uuid")
    }
  }

  static fromObj(obj) {
    return new Friendship(
      User.fromObj(obj.requester),
      User.fromObj(obj.requestee)
    )
  }
}


class BlogComment {
  name
  comment
  rating
  postUuid

  constructor(name, comment, rating, postUuid) {
    this.name = name;
    this.comment = comment;
    this.rating = rating;
    this.postUuid = postUuid;
  }

  static fromObj(obj) {
    return new BlogComment(obj.name, obj.comment, obj.rating, obj.postUuid)
  }
}