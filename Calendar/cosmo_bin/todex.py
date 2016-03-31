#!/usr/bin/python

import os, sys
import subprocess

def run_cmd(cmd):
    print reduce(lambda x, y: x + " " + y, cmd, "")
    p = subprocess.Popen(cmd)
    p.wait()

def convert_to_dex(path):
  cmd = ["mkdir"]
  cmd += [os.path.join(path, "dex")]  
  run_cmd(cmd)

  for root, dirs, files in os.walk(path):
    for f in files:
      if f.endswith(".jar"):
        cmd = ["/scratch/aaasz/adt/sdk/platform-tools/dx", "--dex"]
        cmd += ["--output=" + os.path.join(os.path.join(root, "dex"), os.path.splitext(f)[0] + ".dex")]
        cmd += [os.path.join(root, f)]
        run_cmd(cmd)

def usage():
   print 'todex [input_folder]'

if __name__ == '__main__':

    # check whether we passed in a config file
    if len(sys.argv) < 2:
      usage()
      exit(1)

    path = os.path.dirname(sys.argv[1])
    print 'path = ' + path

    # Convert all jars in the given folder path to dex jars
    convert_to_dex(path)
