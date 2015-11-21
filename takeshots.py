# coding:utf-8

from pyvirtualdisplay import Display
from selenium import webdriver


def main():

    display = Display(visible=0, size=(800, 600))
    display.start()

    browser = webdriver.Firefox()

    browser.get('https://www.ann-kate.jp/')
    browser.save_screenshot('ann-kate.png')

    browser.quit()

    display.stop()


if __name__ == "__main__":
    main()
