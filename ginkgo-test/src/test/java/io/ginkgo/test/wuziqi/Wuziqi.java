package io.ginkgo.test.wuziqi;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * AI五子棋
 * 
 * @since 1.0.0
 * @author Barry
 */
// http://git.oschina.net/ysx_xx/JAVA_FiveChessAi/blob/master/FiveChess/AI%E6%80%9D%E8%B7%AF.txt?dir=0&filepath=FiveChess%2FAI%E6%80%9D%E8%B7%AF.txt&oid=1b74d9719dc5da079839b59fed363955d0d75933&sha=ca6754acf5c2fc590e74fbbf3023e9c69368f50d
public class Wuziqi {
	BufferedImage table;
	BufferedImage black;
	BufferedImage white;

	BufferedImage selected;
	/**
	 * 棋子个数
	 */
	private static int BOARD_SIZE = 15;
	/**
	 * 棋盘宽高
	 */
	private final int TABLE_WIDTH = 535;
	private final int TABLE_HEIGHT = 536;
	/**
	 * 棋盘15等分
	 */
	private final int RATE = TABLE_WIDTH / BOARD_SIZE;
	/**
	 * 棋盘外边距
	 */
	private final int X_OFFSET = 5;
	private final int Y_OFFSET = 6;
	/**
	 * 棋盘
	 */
	private int[][] board = new int[BOARD_SIZE][BOARD_SIZE];
	/**
	 * AI分数
	 */
	private int[][] computerScore = new int[BOARD_SIZE][BOARD_SIZE];
	// private int[][] gamerScore = new int[BOARD_SIZE][BOARD_SIZE];

	JFrame f = new JFrame("五子棋--小熊");

	ChessBoard chessBoard = new ChessBoard();

	private static int selectedX = -1;
	private static int selectedY = -1;
	private static int computerX = -1;
	private static int computerY = -1;

	private static boolean flagGamer = false; // 记录玩家是否赢了
	private static boolean flagComputer = false; // 记录电脑是否赢了

	private static int computerscore = 0; // 电脑最大分数
	private static int comx, comy; // 玩家下子坐标

	private final int HUO = 1;
	private final int CHONG = 2;
	private static int chesscou = 0;
	/**
	 * 记录找到的分数一样的棋子，随机下这些棋子中的一个，以防步法固定
	 */
	private ArrayList<ChessXY> chessList = new ArrayList<ChessXY>();

	Random rand = new Random();

	/**
	 * 
	 * @Title: initto @Description: 重置游戏 @param @return void @throws
	 */
	public void initto() {
		for (int i = 0; i < BOARD_SIZE; ++i) {
			for (int j = 0; j < BOARD_SIZE; ++j) {
				board[i][j] = 0;
				computerScore[i][j] = 100000;
			}
		}
		chesscou = 0;
		computerX = -1;
		computerY = -1;
		flagGamer = false;
		flagComputer = false;
	}

	/**
	 * 
	 * @Title: isRun @Description: 判断该位置是否可以走 @param @param x @param @param
	 *         y @param @return @return boolean @throws
	 */
	public boolean isRun(int x, int y) {
		if (board[x][y] == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @Title: isWin @Description: 判断下该子是否能赢 @param @param f 颜色 @param @param x
	 *         坐标 @param @param y @param @return @return boolean @throws
	 */
	public boolean isWin(int f, int x, int y) {
		int i, count = 1;
		boolean up, down, right, left, rup, lup, rdown, ldown;
		up = down = right = left = rup = lup = rdown = ldown = true;
		/* 上下 */
		for (i = 1; i < 5; ++i) {
			if ((y + i) < BOARD_SIZE) {
				if (board[x][y + i] == f && down)
					count++;
				else
					down = false;
			}
			if ((y - i) >= 0) {
				if (board[x][y - i] == f && up)
					count++;
				else
					up = false;
			}
		}
		if (count >= 5) {
			return true;
		}
		count = 1;
		/* 左右 */
		for (i = 1; i < 5; ++i) {
			if ((x + i) < BOARD_SIZE) {
				if (board[x + i][y] == f && right)
					count++;
				else
					right = false;
			}
			if ((x - i) >= 0) {
				if (board[x - i][y] == f && left)
					count++;
				else
					left = false;
			}
		}
		if (count >= 5) {
			return true;
		}
		count = 1;
		/* 左上右下 */
		for (i = 1; i < 5; ++i) {
			if ((x + i) < BOARD_SIZE && (y + i) < BOARD_SIZE) {
				if (board[x + i][y + i] == f && rdown)
					count++;
				else
					rdown = false;
			}
			if ((x - i) >= 0 && (y - i) >= 0) {
				if (board[x - i][y - i] == f && lup)
					count++;
				else
					lup = false;
			}
		}
		if (count >= 5) {
			return true;
		}
		count = 1;
		/* 右上左下 */
		for (i = 1; i < 5; ++i) {
			if ((x + i) < BOARD_SIZE && (y - i) >= 0) {
				if (board[x + i][y - i] == f && rup)
					count++;
				else
					rup = false;
			}
			if ((x - i) >= 0 && (y + i) < BOARD_SIZE) {
				if (board[x - i][y + i] == f && ldown)
					count++;
				else
					ldown = false;
			}
		}
		if (count >= 5) {
			return true;
		}

		return false;
	}

	/**
	 * 
	 * @Title: Computer_AI @Description: AI下棋 @param @return void @throws
	 */
	public void Computer_AI() {
		computerscore = 0;
		for (int i = 0; i < BOARD_SIZE; ++i) {
			for (int j = 0; j < BOARD_SIZE; ++j) {
				computerScore[i][j] = 0;
				// gamerScore[i][j] = 0;
			}
		}
		getScore();
		for (int i = 0; i < BOARD_SIZE; ++i) {
			for (int j = 0; j < BOARD_SIZE; ++j) {
				if (computerScore[i][j] == computerscore) {
					ChessXY chess = new ChessXY(i, j);
					chessList.add(chess);
				}
			}
		}
		int n = rand.nextInt(chessList.size()); // 电脑根据分值一样的点随机走，防止每次都走相同的步数
		comx = chessList.get(n).x;
		comy = chessList.get(n).y;
		chessList.clear();
	}

	/**
	 * 
	 * @Title: getScore @Description: 评分 @param @return void @throws
	 */
	public void getScore() {
		for (int i = 0; i < BOARD_SIZE; ++i) {
			for (int j = 0; j < BOARD_SIZE; ++j) {
				if (board[i][j] == 0) {
					if (isWin(2, i, j)) // 电脑能赢，故给分最高，因为可以结束，所以不再检测
					{
						computerscore = 13;
						computerScore[i][j] = 13;

						return;
					} else if (isWin(1, i, j)) // 电脑不能赢，玩家能赢，要阻止，所以给12分
					{
						computerscore = 12;
						computerScore[i][j] = 12;
					} else if (isHuoOrChong(2, i, j, 4, HUO)) // 电脑玩家都不能赢，电脑能形成活四，给11分
					{
						computerscore = (computerscore > 11 ? computerscore : 11);
						computerScore[i][j] = 11;
					} else if (isHuoOrChong(2, i, j, 4, CHONG)) // 电脑玩家都不能赢，电脑能形成冲四，给10分
					{
						computerscore = (computerscore > 10 ? computerscore : 10);
						computerScore[i][j] = 10;
					} else if (isHuoOrChong(1, i, j, 4, HUO)) // 电脑玩家都不能赢，玩家能形成活四，给9分
					{
						computerscore = (computerscore > 9 ? computerscore : 9);
						computerScore[i][j] = 9;
					} else if (isHuoOrChong(2, i, j, 3, HUO)) // 电脑玩家都不能赢，电脑能形成活三，给8分
					{
						computerscore = (computerscore > 8 ? computerscore : 8);
						computerScore[i][j] = 8;
					} else if (isHuoOrChong(1, i, j, 4, CHONG)) // 电脑玩家都不能赢，玩家能形成冲四，给7分
					{
						computerscore = (computerscore > 7 ? computerscore : 7);
						computerScore[i][j] = 7;
					} else if (isHuoOrChong(2, i, j, 3, CHONG)) // 电脑玩家都不能赢，电脑能形成冲三，给6分
					{
						computerscore = (computerscore > 6 ? computerscore : 6);
						computerScore[i][j] = 6;
					} else if (isHuoOrChong(2, i, j, 2, HUO)) // 电脑玩家都不能赢，电脑能形成活二，给5分
					{
						computerscore = (computerscore > 5 ? computerscore : 5);
						computerScore[i][j] = 5;
					} else if (isHuoOrChong(1, i, j, 3, CHONG)) // 电脑玩家都不能赢，玩家能形成冲三，给4分
					{
						computerscore = (computerscore > 4 ? computerscore : 4);
						computerScore[i][j] = 4;
					} else if (isHuoOrChong(1, i, j, 2, HUO)) // 电脑玩家都不能赢，玩家能形成活二，给3分
					{
						computerscore = (computerscore > 3 ? computerscore : 3);
						computerScore[i][j] = 3;
					} else if (isHuoOrChong(2, i, j, 2, CHONG)) // 电脑玩家都不能赢，电脑能形成冲二，给2分
					{
						computerscore = (computerscore > 2 ? computerscore : 2);
						computerScore[i][j] = 2;
					} else if (isHuoOrChong(1, i, j, 2, CHONG)) // 电脑玩家都不能赢，玩家能形成冲二，给1分
					{
						computerscore = (computerscore > 1 ? computerscore : 1);
						computerScore[i][j] = 1;
					} else {
						computerScore[i][j] = 0;
					}
				}
			}
		}
	}

	/**
	 * 
	 * @Title: isHuoOrChong @Description: 判断是否为活 @param @param f @param @param
	 *         x @param @param y @param @param num @param @param
	 *         hORc @param @return @return boolean @throws
	 */
	private boolean isHuoOrChong(int f, int x, int y, int num, int hORc) // 活
	{

		num += 1;
		int i, count = 1;
		boolean terminal1 = false;
		boolean terminal2 = false;
		boolean up, down, right, left, rup, lup, rdown, ldown;
		up = down = right = left = rup = lup = rdown = ldown = true;
		/* 上下 */
		for (i = 1; i < num; ++i) {
			if ((y + i) < BOARD_SIZE) {
				if (board[x][y + i] == f && down)
					count++;
				else {
					if (board[x][y + i] == 0 && down) {
						terminal1 = true;
					}
					down = false;
				}
			}
			if ((y - i) >= 0) {
				if (board[x][y - i] == f && up)
					count++;
				else {
					if (board[x][y - i] == 0 && up) {
						terminal2 = true;
					}
					up = false;
				}
			}
		}
		if (count == num - 1 && hORc == HUO && terminal1 && terminal2) {
			return true;
		}
		if (count == num - 1 && hORc == CHONG && ((terminal1 && !terminal2) || (!terminal1 && terminal2))) {
			return true;
		}
		count = 1;
		terminal1 = false;
		terminal2 = false;
		/* 左右 */
		for (i = 1; i < num; ++i) {
			if ((x + i) < BOARD_SIZE) {
				if (board[x + i][y] == f && right)
					count++;
				else {
					if (board[x + i][y] == 0 && right) {
						terminal1 = true;
					}
					right = false;
				}
			}
			if ((x - i) >= 0) {
				if (board[x - i][y] == f && left)
					count++;
				else {
					if (board[x - i][y] == 0 && left) {
						terminal2 = true;
					}
					left = false;
				}
			}
		}
		if (count == num - 1 && hORc == HUO && terminal1 && terminal2) {
			return true;
		}
		if (count == num - 1 && hORc == CHONG && ((terminal1 && !terminal2) || (!terminal1 && terminal2))) {
			return true;
		}
		count = 1;
		terminal1 = false;
		terminal2 = false;
		/* 左上右下 */
		for (i = 1; i < num; ++i) {
			if ((x + i) < BOARD_SIZE && (y + i) < BOARD_SIZE) {
				if (board[x + i][y + i] == f && rdown)
					count++;
				else {
					if (board[x + i][y + i] == 0 && rdown) {
						terminal1 = true;
					}
					rdown = false;
				}
			}
			if ((x - i) >= 0 && (y - i) >= 0) {
				if (board[x - i][y - i] == f && lup)
					count++;
				else {
					if (board[x - i][y - i] == 0 && lup) {
						terminal2 = true;
					}
					lup = false;
				}
			}
		}
		if (count == num - 1 && hORc == HUO && terminal1 && terminal2) {
			return true;
		}
		if (count == num - 1 && hORc == CHONG && ((terminal1 && !terminal2) || (!terminal1 && terminal2))) {
			return true;
		}
		count = 1;
		terminal1 = false;
		terminal2 = false;
		/* 右上左下 */
		for (i = 1; i < num; ++i) {
			if ((x + i) < BOARD_SIZE && (y - i) >= 0) {
				if (board[x + i][y - i] == f && rup)
					count++;
				else {
					if (board[x + i][y - i] == 0 && rup) {
						terminal1 = true;
					}
					rup = false;
				}
			}
			if ((x - i) >= 0 && (y + i) < BOARD_SIZE) {
				if (board[x - i][y + i] == f && ldown)
					count++;
				else {
					if (board[x - i][y + i] == 0 && ldown) {
						terminal2 = true;
					}
					ldown = false;
				}
			}
		}
		if (count == num - 1 && hORc == HUO && terminal1 && terminal2) {
			return true;
		}
		if (count == num - 1 && hORc == CHONG && ((terminal1 && !terminal2) || (!terminal1 && terminal2))) {
			return true;
		}

		return false;
	}

	public void init() throws Exception {
		table = ImageIO.read(new File("image/board.jpg"));
		black = ImageIO.read(new File("image/black.gif"));
		white = ImageIO.read(new File("image/white.gif"));
		selected = ImageIO.read(new File("image/selected.gif"));

		for (int i = 0; i < BOARD_SIZE; ++i) {
			for (int j = 0; j < BOARD_SIZE; ++j) {
				board[i][j] = 0;
				computerScore[i][j] = 0;
			}
		}
		chessBoard.setPreferredSize(new Dimension(TABLE_WIDTH, TABLE_HEIGHT));

		chessBoard.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int xPos = (int) ((e.getX() - X_OFFSET) / RATE);
				int yPos = (int) ((e.getY() - Y_OFFSET) / RATE);
				// System.out.println("1 " + xPos + " " + yPos);
				if (isRun(xPos, yPos)) {
					flagGamer = isWin(1, xPos, yPos);
					board[xPos][yPos] = 1;
					chesscou++;
					// do //电脑下棋，随机
					// {
					// comx = (rand.nextInt(535) - X_OFFSET) / RATE;
					// comy = (rand.nextInt(536) - Y_OFFSET) / RATE;
					// } while (!isRun(comx, comy));
					if (chesscou == (BOARD_SIZE * BOARD_SIZE)) {
						JOptionPane.showMessageDialog(null, "不相上下！！！\n再来一盘吧！！！", "结束", JOptionPane.ERROR_MESSAGE);
						initto();
					} else {
						Computer_AI(); // 电脑下棋，AI算法
						chesscou++;
						// System.out.println("2 " + comx + " " + comy);
						flagComputer = isWin(2, comx, comy);
						board[comx][comy] = 2;
						computerX = comx;
						computerY = comy;
					}
				}
				chessBoard.repaint();
				if (flagGamer) {
					JOptionPane.showMessageDialog(null, "厉害厉害！！！\n你赢了！！！", "结束", JOptionPane.ERROR_MESSAGE);
					initto();
				} else if (flagComputer) {
					JOptionPane.showMessageDialog(null, "哈哈哈哈！！！\n你输了！！！", "结束", JOptionPane.ERROR_MESSAGE);
					initto();
				}
			}

			public void mouseExited(MouseEvent e) {
				selectedX = -1;
				selectedY = -1;
				chessBoard.repaint();
			}
		});
		chessBoard.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				selectedX = (e.getX() - X_OFFSET) / RATE;
				selectedY = (e.getY() - Y_OFFSET) / RATE;

				chessBoard.repaint();
			}
		});
		f.add(chessBoard);
		f.setCursor(new Cursor(Cursor.HAND_CURSOR));
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		f.pack();
		f.setVisible(true);
	}

	public static void main(String[] args) throws Exception {
		Wuziqi game = new Wuziqi();
		game.init();
	}

	@SuppressWarnings("serial")
	class ChessBoard extends JPanel {
		public void paint(Graphics g) {
			g.drawImage(table, 0, 0, null);
			if (selectedX >= 0 && selectedY >= 0) {
				g.drawImage(selected, selectedX * RATE + X_OFFSET, selectedY * RATE + Y_OFFSET, null);
			}
			if (computerX >= 0 && computerY >= 0) {
				g.drawImage(selected, computerX * RATE + X_OFFSET, computerY * RATE + Y_OFFSET, null);
			}
			for (int i = 0; i < BOARD_SIZE; ++i) {
				for (int j = 0; j < BOARD_SIZE; ++j) {
					if (board[i][j] == 1) {
						g.drawImage(black, i * RATE + X_OFFSET, j * RATE + Y_OFFSET, null);
					}
					if (board[i][j] == 2) {
						g.drawImage(white, i * RATE + X_OFFSET, j * RATE + Y_OFFSET, null);
					}
				}
			}
		}
	}
}

class ChessXY {
	int x;
	int y;

	public ChessXY(int x, int y) {
		this.x = x;
		this.y = y;
	}
}