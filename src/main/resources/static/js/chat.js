/**
 * Chat client UI
 */

function messageElemFromMessage(message, selfUuid, friendUuid) {
  const eMessageLi = document.createElement('li')
  eMessageLi.classList.add('chat-message-container')
  const eMessageSpan = document.createElement('span')
  eMessageSpan.classList.add('chat-message')
  if (message.sender.uuid === selfUuid
    && message.receiver.uuid === friendUuid) {
    eMessageSpan.classList.add('chat-message--self')
  } else if (message.sender.uuid === friendUuid
    && message.receiver.uuid === selfUuid
  ) {
    eMessageSpan.classList.add('chat-message--other')
  } else {
    return undefined
  }
  eMessageSpan.innerText = message.text
  eMessageLi.appendChild(eMessageSpan)
  return eMessageLi
}


window.addEventListener('load', () => {
  getSelfUser().then(selfUser => {
    const selfUuid = selfUser.uuid
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

    // == Friends ==

    const eChatScreen = document.getElementById('chat-screen')
    const eChatBackBtn = document.getElementById('chat-back-btn')
    const eMessageList = document.getElementById('message-list')
    const eSearchBtn = document.getElementById('chat-search-btn')
    const eSearchChatScreen = document.getElementById('search-chat-screen')
    const eSearchBackBtn = document.getElementById('search-back-btn')
    eChatBackBtn.addEventListener('click', () => {
      $(eChatScreen).hide('blind')
      $(eSearchChatScreen).hide('blind')
      chatFriend = undefined
    })

    eSearchBtn.addEventListener('click', () => {
      $(eChatScreen).show('blind')
      $(eSearchChatScreen).show('blind')
    })

    eSearchBackBtn.addEventListener('click', () => {
      $(eChatScreen).show('blind')
      $(eSearchChatScreen).hide('blind')
    })

    const friendsTblUpdateTimeout = 1500
    let chatFriend = undefined
    let chatLastMsgI = -1
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
          const eTdChat = document.createElement('td')
          const eChatBtn = document.createElement('button')
          eChatBtn.innerText = "chat"
          $(eChatBtn).on('click', () => {
            eMessageList.innerHTML = ''
            $(eChatScreen).toggle('blind')
            chatFriend = friend
            chatLastMsgI = -1
            forceUpdateChat()
          })
          eTdChat.appendChild(eChatBtn)
          eTr.appendChild(eTdChat)
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

    // == Chat ==

    let chatUpdateBusy = false
    function updateChat() {
      if (chatUpdateBusy)
        return
      forceUpdateChat()
    }

    const chatUpdateTimeout = 1500
    let nextChatUpdate = -Infinity
    function forceUpdateChat() {
      chatUpdateBusy = true
      // noinspection EqualityComparisonWithCoercionJS
      if (chatFriend == undefined) {
        if (nextChatUpdate < Date.now()) {
          nextChatUpdate = Date.now() + chatUpdateTimeout
          window.setTimeout(updateChat, chatUpdateTimeout)
        }
        chatUpdateBusy = false
        return
      }
      getSelfMessages().then(messages => {
        for (let i = chatLastMsgI + 1; i < messages.length; i++) {
          const message = messages[i]
          const eMessageLi = messageElemFromMessage(message, selfUuid, chatFriend.uuid)
          // noinspection EqualityComparisonWithCoercionJS
          if (eMessageLi == undefined)
            continue
          eMessageList.append(eMessageLi)
          chatLastMsgI = i
        }
        if (nextChatUpdate < Date.now()) {
          nextChatUpdate = Date.now() + chatUpdateTimeout
          window.setTimeout(updateChat, chatUpdateTimeout)
        }
        chatUpdateBusy = false
      })
    }

    const eMsgForm = document.getElementById('message-form')
    const eMsgTxt = document.getElementById('message-txt')
    eMsgForm.addEventListener('submit', e => {
      e.preventDefault()
      // noinspection EqualityComparisonWithCoercionJS
      if (chatFriend == undefined) {
        return
      }
      const text = eMsgTxt.value
      eMsgTxt.value = ''
      const msg = new Message(text, selfUser, chatFriend)
      postChatMessage(msg)
      forceUpdateChat()
    })

    // == Chat search ==

    const eSearchMessageList = document.getElementById('search-message-list')
    function updateMsgSearch(query) {
      eSearchMessageList.innerHTML = ''
      getSelfMessagesFiltered(query).then(messages => {
        for (const message of messages) {
          const eMessageLi = messageElemFromMessage(message, selfUuid, chatFriend.uuid)
          // noinspection EqualityComparisonWithCoercionJS
          if (eMessageLi == undefined)
            continue
          eSearchMessageList.append(eMessageLi)
        }
        if (nextChatUpdate < Date.now()) {
          nextChatUpdate = Date.now() + chatUpdateTimeout
          window.setTimeout(updateChat, chatUpdateTimeout)
        }
        chatUpdateBusy = false
      })
    }

    const eSearchMsgForm = document.getElementById('search-message-form')
    const eSearchMsgTxt = document.getElementById('search-message-txt')
    eSearchMsgForm.addEventListener('submit', e => {
      e.preventDefault()
      // noinspection EqualityComparisonWithCoercionJS
      if (chatFriend == undefined) {
        return
      }
      const query = eSearchMsgTxt.value
      eSearchMsgTxt.value = ''
      updateMsgSearch(query)
    })

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


    const eAddFriendBtn = document.getElementById('add-friend-btn')
    const eAddFriendPanel = document.getElementById('add-friend-panel')
    const eChangeStatusBtn = document.getElementById('change-status-btn')
    const eChangeStatusPanel = document.getElementById('change-status-panel')
    eAddFriendBtn.addEventListener('click', () => {
      $(eChangeStatusPanel).hide('blind')
      $(eAddFriendPanel).toggle('blind')
    })
    eChangeStatusBtn.addEventListener('click', () => {
      $(eAddFriendPanel).hide('blind')
      $(eChangeStatusPanel).toggle('blind')
    })

  }).catch(console.error)
})
