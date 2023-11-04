import sys, getopt

def main(argv):
  """
  Reads arguments and passes the call on to the corresponding functions.
  """
  opts, args = getopt.getopt(argv,"h",[])

  for opt, arg in opts:
    if opt == '-h':
      help()
      sys.exit()

  help()

def help():
  """
  Prints help message.
  """
  print("d11 <command>\n\n" +
        "Usage:\n\n"
        " d11 -h      Print this help message")

if __name__ == "__main__":
   main(sys.argv[1:])
