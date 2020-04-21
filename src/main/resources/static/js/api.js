/**
 * Chat REST-API AJAX client
 */


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


/**
 * Call an API endpoint.
 * @returns {Promise<Object>}
 */
function apiCall(endpoint, method, data) {
  const url = `/api/${endpoint}`;
  return new Promise((resolve, reject) => {
    const req = new XMLHttpRequest();
    req.open(method, url);
    req.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    req.addEventListener('loadend', () => {
      if (req.status !== 200)
        reject(new Error(
          `API request failed for endpoint "${endpoint}": XHR error: HTTP status ${req.status}`
        ));

      resolve(JSON.parse(req.responseText));
    })
    if (data != null)
      req.send(JSON.stringify(data))
    else
      req.send();
  });
}

/**
 * Get data at an API endpoint.
 * @returns {Promise<Object>}
 */
function apiGet(endpoint) {
  return apiCall(endpoint, 'GET')
}

/**
 * Put data at an API endpoint.
 * @returns {Promise<Object>}
 */
function apiPut(endpoint, data) {
  return apiCall(endpoint, 'PUT', data)
}

/**
 * Put data at an API endpoint.
 * @returns {Promise<Object>}
 */
function apiPost(endpoint, data) {
  return apiCall(endpoint, 'POST', data)
}


/**
 * Returns self user.
 * @returns {Promise<User>}
 */
function getSelfUser() {
  return new Promise((resolve, reject) => apiGet('user/self')
    .then(data => resolve(User.fromObj(data)))
    .catch(reject)
  )
}

/**
 * Updates self user.
 * @returns {Promise<User>}
 */
function putSelfUser(user) {
  return new Promise((resolve, reject) =>apiPut('user/self', user)
    .then(data => resolve(User.fromObj(data)))
    .catch(reject)
  )
}

/**
 * Returns own friends.
 * @returns {Promise<Array<User>>}
 */
function getFriends() {
  return new Promise((resolve, reject) => apiGet('user/self/friends')
    .then(data => resolve(data.map(User.fromObj)))
    .catch(reject)
  )
}

/**
 * Creates a new friendship.
 * @returns {Promise<Friendship>}
 */
function postFriendship(username) {
  return new Promise((resolve, reject) => apiPost('friendship',
    {
      requestee: {
        username
      }
    })
    .then(data => resolve(Friendship.fromObj(data)))
    .catch(reject)
  )
}
