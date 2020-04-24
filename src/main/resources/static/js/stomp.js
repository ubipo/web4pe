/**
 * Chat STOMP over WebSocket client
 *
 * See: https://stomp.github.io/stomp-specification-1.2.html
 */


class Stomp {
  ws
  subscriptions
  subscriptionId
  connectedCallbacks

  constructor() {
    this.ws = undefined
    this.subscriptions = {}
    this.subscriptionId = 0
    this.connectedCallbacks = []
  }

  static marshalPayload(command, headers, data = undefined) {
    const headerStrs = Object.entries(headers)
      .map(kv => kv.join(':'))
    const headersStr = headerStrs.join('\n')
    const parts = [
      command.toUpperCase(),
      headersStr,
      '', // Empty line before body
      '\0'
    ]
    // noinspection EqualityComparisonWithCoercionJS
    if (data != undefined)
      parts.splice(-1, 0, data)
    return parts.join('\n')
  }

  /**
   *
   * @param payload {string}
   */
  static unmarshalPayload(payload) {
    const iEndOfHeaders = payload.indexOf('\n\n')
    const commandHeaders = payload.slice(0, iEndOfHeaders).split('\n')
    const command = commandHeaders[0]
    const headersStrs = commandHeaders.slice(1)
    const headers = headersStrs.map(val => val.split(':'))
    const headersObj = Object.fromEntries(headers)
    const json = payload.slice(iEndOfHeaders + 2, -1)
    const data = json.trim() === '' ? null : JSON.parse(json)
    return {
      command: command,
      headers: headersObj,
      data: data
    }
  }

  send(destination, data) {
    const payload = Stomp.marshalPayload('SEND', {
      destination: destination
    }, JSON.stringify(data))
    this.ws.send(payload)
  }

  subscribe(destination, callback) {
    let existing = this.subscriptions[destination]
    // noinspection EqualityComparisonWithCoercionJS
    if (existing == undefined) {
      existing = []
      const payload = Stomp.marshalPayload('SUBSCRIBE', {
        id: this.subscriptionId++,
        destination: destination
      })
      this.ws.send(payload)
    }
    existing.push(callback)
    this.subscriptions[destination] = existing
  }

  connect() {
    return new Promise((resolve, reject) => {
      this.ws = new WebSocket("ws://localhost:8080/ws");
      const self = this
      this.ws.addEventListener('message', msg => self.__msgHandler(msg))
      this.ws.addEventListener('open', () => {
        this.connectedCallbacks.push(() => {
          resolve()
        })
        const payload = Stomp.marshalPayload('CONNECT', {
          'accept-version': 1.2
        })
        this.ws.send(payload)
      })
    })
  }

  __msgHandler(msg) {
    const unmarshalled = Stomp.unmarshalPayload(msg.data)
    if (unmarshalled.command === 'MESSAGE') {
      const destination = unmarshalled.headers.destination
      const callbacks = this.subscriptions[destination]
      for (const callback of callbacks) {
        callback(unmarshalled.data)
      }
    } else if (unmarshalled.command === 'CONNECTED') {
      for (const callback of this.connectedCallbacks) {
        callback()
      }
    }
  }
}
