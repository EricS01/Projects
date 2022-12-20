import tweepy
import keys
import random
from datetime import date


def api():
    auth = tweepy.OAuth1UserHandler(keys.api_key, keys.api_secret)
    auth.set_access_token(keys.access_token, keys.access_token_secret)

    return tweepy.API(auth)


def tweet(api: tweepy.API, message: str, image_path=None):
    if image_path:
        api.update_status_with_media(message, image_path)

    else:
        api.update_status(message)

    print("Tweeted Successfully!")

if __name__ == '__main__':
    
    fd = open("tips.txt", "r")
    lines = fd.readlines()
    quote = random.choice(lines)
    fd.close()
    startDate = date(2022, 12, 17)
    delta = date.today() - startDate
    message = "Day " + str(delta.days) + " of Money Tips: " + quote
    api = api()
    tweet(api, message)
    