/**
 * Chat client UI
 */


window.addEventListener('load', () => {
  const eStatusLbl = document.getElementById('status-lbl')

  function updateStatusLbl() {
    getSelfUser().then(selfUser => {
      eStatusLbl.innerText = selfUser.status
    }).catch(console.error)
  }

  updateStatusLbl()

  function updateStatus(newStatus) {
    const user = new User()
    user.status = newStatus
    putSelfUser(user).then(selfUser => {
      eStatusLbl.innerText = selfUser.status
    }).catch(console.error)
  }

  const eStatusForm = document.getElementById('status-form');
  const eStatusTxt = document.getElementById('status-txt');
  eStatusForm.addEventListener('submit', e => {
    e.preventDefault()
    const status = eStatusTxt.value
    updateStatus(status)
  })

  const eFriendsTbl = document.getElementById('friends-tbl')
  let friendsTblUpdateBusy = false
  function updateFriendsTbl() {
    if (friendsTblUpdateBusy)
      return
    forceUpdateFriendsTbl()
  }

  const friendsTblUpdateTimeout = 1500
  function forceUpdateFriendsTbl() {
    friendsTblUpdateBusy = true
    getFriends().then(friends => {
      eFriendsTbl.innerHTML = ''
      for (const friend of friends) {
        const eTr = document.createElement('tr')
        const eTdName = document.createElement('td')
        eTdName.innerText = friend.name
        eTr.appendChild(eTdName)
        const eTdStatus = document.createElement('td')
        eTdStatus.innerText = friend.status
        eTr.appendChild(eTdStatus)
        eFriendsTbl.appendChild(eTr)
      }
      window.setTimeout(updateFriendsTbl, friendsTblUpdateTimeout)
      friendsTblUpdateBusy = false
    }).catch(err => {
      console.error(err)
      friendsTblUpdateBusy = false
    })
  }

  updateFriendsTbl()

  function createFriendship(username) {
    postFriendship(username).then(() => {
      forceUpdateFriendsTbl()
    }).catch(console.error)
  }

  const eFriendForm = document.getElementById('friend-form');
  const eFriendTxt = document.getElementById('friend-txt');
  eFriendForm.addEventListener('submit', e => {
    e.preventDefault()
    const username = eFriendTxt.value
    createFriendship(username)
    // updateStatus(status)
  })
})
