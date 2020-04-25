/**
 * Chat REST-API AJAX client
 */


/**
 * Call an API endpoint.
 * @returns {Promise<Object>}
 */
function apiCall(endpoint, method, data) {
  const url = `/api/${endpoint}`;
  return new Promise((resolve, reject) => {
    const req = new XMLHttpRequest();
    req.open(method, url);
    req.setRequestHeader('Content-Type', 'application/json;charset=UTF-8');
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
 * Call an API endpoint using jQuery.
 * @returns {Promise<Object>}
 */
function apiCallJquery(endpoint, method, data) {
  const url = `/api/${endpoint}`;
  return new Promise((resolve, reject) => {
    const req = $.ajax(url, {
      method: method,
      contentType: 'application/json;charset=UTF-8',
      data: data != null ? JSON.stringify(data) : undefined
    })
    req.done(res => {
      resolve(JSON.parse(req.responseText));
    })
    req.fail(xhr => {
      reject(new Error(
        `API request failed for endpoint "${endpoint}": XHR error: HTTP status ${xhr.status}`
      ));
    })
  });
}

/**
 * Call an API endpoint using jQuery.
 * @returns {Promise<Object>}
 */
function apiGetJquery(endpoint, data) {
  const url = `/api/${endpoint}`;
  return new Promise((resolve, reject) => {
    const req = $.get({
      url: url,
      contentType: 'application/json;charset=UTF-8',
      data: data
    })
    req.done(res => {
      resolve(JSON.parse(req.responseText));
    })
    req.fail(xhr => {
      reject(new Error(
        `API request failed for endpoint "${endpoint}": XHR error: HTTP status ${xhr.status}`
      ));
    })
  });
}

/**
 * Returns self user.
 * @returns {Promise<User>}
 */
function getSelfUser() {
  return new Promise((resolve, reject) => apiCall('user/self', 'GET')
    .then(data => resolve(User.fromObj(data)))
    .catch(reject)
  )
}

/**
 * Updates self user.
 * @returns {Promise<User>}
 */
function putSelfUser(user) {
  return new Promise((resolve, reject) => apiCall('user/self', 'PUT', user)
    .then(data => resolve(User.fromObj(data)))
    .catch(reject)
  )
}

/**
 * Returns own friends.
 * @returns {Promise<Array<User>>}
 */
function getFriends() {
  return new Promise((resolve, reject) => apiCall('user/self/friends', 'GET')
    .then(data => resolve(data.map(User.fromObj)))
    .catch(reject)
  )
}

/**
 * Creates a new friendship.
 * @returns {Promise<Friendship>}
 */
function postFriendship(username) {
  return new Promise((resolve, reject) => apiCall('friendship', 'POST',
    {
      requestee: {
        username
      }
    })
    .then(data => resolve(Friendship.fromObj(data)))
    .catch(reject)
  )
}

/**
 * Returns all messages involving self.
 * @returns {Promise<Array<Message>>}
 */
function getSelfMessages() {
  return new Promise((resolve, reject) => apiCallJquery('message/self', 'GET')
    .then(data => resolve(data.map(Message.fromObj)))
    .catch(reject)
  )
}

function postChatMessage(message) {
  return new Promise((resolve, reject) => apiCallJquery('message', 'POST', message)
    .then(data => resolve(Message.fromObj(data)))
    .catch(reject)
  )
}

/**
 * Returns all messages involving self.
 * @returns {Promise<Array<Message>>}
 */
function getSelfMessagesFiltered(query) {
  return new Promise((resolve, reject) => apiGetJquery('message/self', {
      fuzzyText: query
    })
    .then(data => resolve(data.map(Message.fromObj)))
    .catch(reject)
  )
}
