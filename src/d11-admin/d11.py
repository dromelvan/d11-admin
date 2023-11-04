import sys, getopt
import photos

def main(argv):
  """
  Reads arguments and passes the call on to the corresponding functions.
  """
  opts, args = getopt.getopt(argv,"h",["photos"])

  for opt, arg in opts:
    if opt == '-h':
      help()
      sys.exit()

  for arg in args:
    if arg == 'photos':
      photos.update_player_photos()
      sys.exit()

  help()

def help():
  """
  Prints help message.
  """
  print("d11 <command>\n\n" +
        "Usage:\n\n"
        " d11 -h      Print this help message\n" +
        " d11 photos  Update player photos")

if __name__ == "__main__":
   main(sys.argv[1:])
