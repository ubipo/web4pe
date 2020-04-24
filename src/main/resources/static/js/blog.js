/**
 * Blog client UI
 */


window.addEventListener('load', () => {
  const ePostSections = document.getElementsByClassName('post-sections')
  const s = new Stomp()
  s.connect().then(() => {
    console.log('Connected!')
    const commentsLists = {}
    for (const ePostSection of Array.from(ePostSections)) {
      const uuid = ePostSection.getAttribute('data-uuid')
      commentsLists[uuid] = ePostSection.querySelector(`#comments-list--${uuid}`)
      const eCommentForm = ePostSection.querySelector(`#comment-form--${uuid}`)
      const eNameTxt = eCommentForm.querySelector(`#comment-form__name--${uuid}`)
      const eCommentTxt = eCommentForm.querySelector(`#comment-form__comment--${uuid}`)
      const eRatingNbr = eCommentForm.querySelector(`#comment-form__rating--${uuid}`)
      eCommentForm.addEventListener('submit', e => {
        e.preventDefault()
        const name = eNameTxt.value
        const comment = eCommentTxt.value
        const rating = eRatingNbr.value
        s.send('/comment/create', new BlogComment(name, comment, rating, uuid))
      })
    }
    s.subscribe('/comment', data => {
      const comment = BlogComment.fromObj(data)
      const eCommentsList = commentsLists[comment.postUuid]
      const eComment = document.createElement('li')
      eComment.innerText = `${comment.name}: "${comment.comment}" - ${comment.rating}`
      eCommentsList.appendChild(eComment)
    })
  }).catch(console.error)
})

