import sys, getopt
import photos
import whoscored
import d11_fixtures
import logging

def main(argv):
  """
  Reads arguments and passes the call on to the corresponding functions.
  """
  opts, args = getopt.getopt(argv,"h",["photos"])

  logging.basicConfig(level = logging.INFO, format = '%(message)s')

  for opt, arg in opts:
    if opt == '-h':
      help()
      sys.exit()

  for arg in args:
    if arg == 'photos':
      photos.update_player_photos()
      sys.exit()
    if arg == 'pl_fixtures':
      whoscored.parse_fixtures()
      sys.exit()
    if arg == 'd11_fixtures':
      d11_fixtures.generate_fixtures()
      sys.exit()

  help()

def help():
  """
  Prints help message.
  """
  logging.info("d11 <command>\n\n" +
               "Usage:\n\n"
               " d11 -h          Print this help message\n" +
               " d11 photos      Update player photos\n" +
               " d11 pl_fixtures Parse Premier League fixtures")

if __name__ == "__main__":
   main(sys.argv[1:])
