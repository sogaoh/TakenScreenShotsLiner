# coding:utf-8

import ConfigParser
import os
import sys
import csv

from pyvirtualdisplay import Display
from selenium import webdriver


def read_ini(ini_file):
    ini_obj = ConfigParser.SafeConfigParser()
    if os.path.exists(ini_file):
        ini_obj.read(ini_file)
    else:
        sys.stderr.write('File : %s is NOT found.' % ini_file)
        sys.exit(-1)

    print 'read_ini DONE.'
    return ini_obj


def confirm_path(ini_obj):
    docroot = ini_obj.get('path', 'apache.docroot')
    if not os.path.exists(docroot):
        os.system('sudo mkdir -p ' + docroot)
        print 'mkdir -p ' + docroot + ' DONE.'
    htmldir = ini_obj.get('path', 'output.htmldir')
    if not os.path.exists(htmldir):
        os.system('sudo mkdir -p ' + htmldir)
        print 'mkdir -p ' + htmldir + ' DONE.'
    shotsdir = ini_obj.get('path', 'output.shotsdir')
    if not os.path.exists(shotsdir):
        os.system('sudo mkdir -p ' + shotsdir)
        print 'mkdir -p ' + shotsdir + ' DONE.'

    print 'confirm_path DONE.'


def read_csv(csv_file):
    if not os.path.exists(csv_file):
        sys.exit(-2)

    csv_obj = []
    with open(csv_file, 'r') as f:
        reader = csv.reader(f)
        for row in reader:
            if row[0].startswith('#'):
                continue
            csv_obj.append(row)

    print 'read_csv DONE.'
    return csv_obj


def pre(ini_file="tssl.ini", csv_file="tssl.csv"):
    ini = read_ini(ini_file)
    confirm_path(ini)
    targets = read_csv(csv_file)

    rv = []
    rv.append(ini)
    rv.append(targets)

    return rv


def main(param):

    if len(param) != 2:
        sys.exit(-9)
    if len(param[1]) <= 0:
        sys.exit(-8)
    paths = param[0]
    shotsdir = paths.get('path', 'output.shotsdir')
    targets = param[1]

    display = Display(visible=0, size=(800, 600))
    display.start()

    browser = webdriver.Firefox()

    for tgt in targets:
        browser.get(tgt[0])
        browser.save_screenshot(shotsdir+'/'+tgt[1]+'.png')
        print 'Took '+tgt[1]+'.png'

    browser.quit()

    display.stop()


if __name__ == "__main__":
    param = pre()
    main(param)
