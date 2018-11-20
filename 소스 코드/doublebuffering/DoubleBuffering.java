package doublebuffering;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ys.db.InsertRank;
import ys.db.ShowRank;

public class DoubleBuffering extends JFrame implements Runnable {

	public static boolean THREAD_STATUS = true; // 쓰레드 상태 주기 
	// ==============
	boolean returnPosition = true; // 다시 적을 생성할수 있도록 신호를 주는것
	// 캐릭터 위치, 점수, 더블 버퍼링, 이미지 변수, 마우스 변수, 승리 판정 변수 선언
	private Map<Integer, Integer> map = null; // 적 리스트 만들꺼
	Image enemy = new ImageIcon(getClass().getResource("/image/enemy.png")).getImage(); // 연근 이미지 불러오는 작업
	int enemyY = 50;
	int[] enemyXList = new int[15]; // 15개의 적들에 x값 좌표
	// 여기까지 적 만들기 ==============
	int x, y, xDirection, yDirection,rectX, rectY;
	public static int SCORE = 0; // 점수 
	Image dbImage;
	Graphics dbg;
	Image face, coin, icon;
	boolean mouseOnScreen;
	boolean won = false; //이겼을때 
	boolean loose = false; // 졌을때 
	boolean gogo = false; // thread_status 제어

	// 기본적인 설정
	public final static int WIDTH = 1600; // 가로 화면 크기
	public final static int HEIGHT = 900; // 세로 화면크기

	private final int circleSize = 32; // 스마일 게임 케릭터
	private final int rectSize = 32; // 동전 먹는거 판단 동전의 크기를 말한다 .
	private final Font font = new Font("맑은 고딕", Font.BOLD | Font.ITALIC, 30);

	// 음악 관련 설정
	// static InputStream IN;
	// static AudioStream BGM;
	// static AudioPlayer MGP = AudioPlayer.player;

	// 변경시킬 패널들
	public JPanel endViewer = null;
	public JPanel showRank = null;
	public JPanel insertRank = null;

	// 쓰레드 실행 함수
	public void run() {

		try {
			if (THREAD_STATUS) {
				while (true) {

					move();
					Thread.sleep(1); // 스피드 조절
				}
			}
		} catch (Exception e) {
			System.out.println("오류가 발생했습니다.");
		}
	}

	// 실제로 캐릭터가 이동하는 함수
	public void move() {
		x += xDirection;
		y += yDirection;
		if (x <= 0)
			x = 0;
		if (x >= WIDTH - circleSize)
			x = WIDTH - circleSize;
		if (y <= circleSize)
			y = circleSize;
		if (y >= WIDTH - circleSize)
			y = WIDTH - circleSize;
	}

	// 방향 지정
	public void setXDirection(int xdir) {
		xDirection = xdir;
	}

	public void setYDirection(int ydir) {
		yDirection = ydir;
	}

	// 마우스 이벤트 처리
	public class MO extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			int xCoord = e.getX(); // 마우스 클릭시 클릭시점에 x 축 좌표 봔환
			int yCoord = e.getY(); // 마우스 클릭시 클릭시점에 y 축 좌표 반환
			x = xCoord - circleSize / 2;
			y = yCoord - circleSize / 2;
		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			mouseOnScreen = true;
		}

		@Override
		public void mouseExited(MouseEvent e) {
			mouseOnScreen = false;
		}
	}
	// ===========================================

	// 키보드 이벤트 처리
	public class AL implements KeyEventDispatcher {
		public boolean dispatchKeyEvent(KeyEvent e) {
			if (e.getID() == KeyEvent.KEY_PRESSED) {
				// 키보들르 눌렀을때 처리
				int keyCode = e.getKeyCode();
				if (keyCode == e.VK_LEFT) {
					setXDirection(-1);
				}
				if (keyCode == e.VK_RIGHT) {
					setXDirection(+1);
				}
				if (keyCode == e.VK_UP) {
					setYDirection(-1);
				}
				if (keyCode == e.VK_DOWN) {
					setYDirection(+1);
				}
			}
			if (e.getID() == KeyEvent.KEY_RELEASED) {
				int keyCode = e.getKeyCode();
				if (keyCode == e.VK_LEFT) {
					setXDirection(0);
				}
				if (keyCode == e.VK_RIGHT) {
					setXDirection(0);
				}
				if (keyCode == e.VK_UP) {
					setYDirection(0);
				}
				if (keyCode == e.VK_DOWN) {
					setYDirection(0);
				}
			}
			return false;
		}
	}

	public void enCreate() {
		// 적 생성 매소드
		Random r = new Random();
		for (int i = 0; i < 15; i++) {
			int value = r.nextInt(DoubleBuffering.WIDTH);
			map.put(i, value); // enemy x 축생성
			this.enemyXList[i] = value;
		}
	}

	public void moveEn() {
		// 적 떨어 트리기 !!!!
		if (enemyY == 50) {
			returnPosition = true;
			enCreate();
		}

		if (returnPosition) {
			enemyY = enemyY + (int) (Math.random() * 3); // 적 떨어지는 속도 ..컨트롤
			if (enemyY > HEIGHT) {
				returnPosition = false;
			}
		} else
			enemyY = 50;
		System.out.println(enemyY);

	}
	// ======================================
	// 생성자

	public DoubleBuffering() {
		// 이미지 불러오기

		map = new HashMap<Integer, Integer>();
		this.enCreate();

		ImageIcon faceIcon = new ImageIcon(getClass().getResource("/image/face.png"));
		face = faceIcon.getImage();
		ImageIcon coinIcon = new ImageIcon(getClass().getResource("/image/coin.png"));
		coin = coinIcon.getImage();
		ImageIcon iconIcon = new ImageIcon(getClass().getResource("/image/icon.png"));
		icon = iconIcon.getImage();

		// 게임 기초 설정
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		System.out.println(manager);
		manager.addKeyEventDispatcher(new AL());
		addMouseListener(new MO());
		Toolkit kit = Toolkit.getDefaultToolkit();
		this.setIconImage(icon); // 아이콘 이미지 세팅

		setTitle("더블 버퍼링");
		setSize(WIDTH, HEIGHT);
		setResizable(false); // 사용자가 맘대로 크기를 줄이지 못함
		setVisible(true);
		setBackground(Color.DARK_GRAY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // 중앙
		// 캐릭터 및 장애물 초기 설정
		// 캐릭 위치 중앙에 위치
		x = WIDTH / 2;
		y = HEIGHT / 2;

		rectX = new Random().nextInt(WIDTH - rectSize); // 랜덤한 x 좌표에 동전 생성
		rectY = new Random().nextInt(HEIGHT - rectSize * 2) + rectSize; // 랜덤한 y 좌표에 동전 생성

	}

	// 더블 버퍼링을 이용해 실제로 그리는 함수
	public void paint(Graphics g) {
		dbImage = createImage(WIDTH, HEIGHT);
		dbg = dbImage.getGraphics();
		paintComponent(dbg);
		g.drawImage(dbImage, 0, 0, this);
	}

	// 더블 버퍼링에 사용되는 그리기 컴포넌트
	public void paintComponent(Graphics g) {
		// 먼저 호출하고 , paint 메소드를 호출 하지만 끝나기 전까지는 계속 paintComponent를 호출 동시에 paint도 호출
		// enemy 클래스한테 객체 전달\
		// if(enemyClass.THREAD_SIGNAL) {
		// System.out.println("start");
		// Thread t2 = new Thread(new EnemyClass());
		// t2.start();
		// }

		// 캐릭터와 위치 정보 나타내기 그리고 코인 나타내기
		g.setFont(font);
		g.setColor(Color.WHITE);

		// 승리 판정 이김
		if (SCORE >= 100) {
			g.drawString("승리하셨습니다!", 2, 62);
			won = true;
			gogo = true;
			this.endDrawScreen(g);
		} else
			g.drawString("현재 위치 : (" + x + ", " + y + ") 점수 : " + SCORE, 2, 62);
		// 그려주는 부분
		g.drawImage(face, x, y, this); // 얼굴 그려주기
		g.drawImage(coin, rectX, rectY, this); // 코인 그려주기

		// 적 그려주기
		for (int i = 0; i < map.size(); i++) {
			g.drawImage(enemy, map.get(i), enemyY, this);
		}

		moveEn(); // 떨어지게 + 값을 주어서 떨어지게 한다.
		// g.drawImage(enemyClass.enemyList.get(0), enemyClass.getX() + 1 ,
		// enemyClass.getY(),this); //적 그려주기 ...
		// System.out.println(enemyClass.getX() + " " + enemyClass.getY());

		// 코인과 캐릭터 충돌 감지
		if (rectX - circleSize < x && x < rectX + rectSize && rectY - circleSize < y && y < rectY + rectSize) {
			// 스코어 올리고 위치 다시 재정의

			System.out.println("============================================");
			System.out.println("판정 위치 x : " + rectX);
			System.out.println("판정 위치 y : " + rectY);
			System.out.println("rectSize " + rectSize);
			System.out.println("circleSize : " + circleSize);
			System.out.println("x : " + x + "  y : " + y);
			System.out.println("============================================");

			SCORE++;
			rectX = new Random().nextInt(WIDTH - rectSize);

			rectY = new Random().nextInt(HEIGHT - rectSize * 2) + rectSize;
			// 여기까지 재정의 코드

		}
		// 적의 x 축과 적의 가로 세로 크기 와 코인의 충돌 비교 판단 잡아먹힘
		for (int i = 0; i < enemyXList.length; i++) {
			if (this.enemyXList[i] - circleSize < x && x < this.enemyXList[i] + rectSize && enemyY - circleSize < y
					&& y < enemyY + rectSize) {
				g.drawString("잡아 먹혔어요 !!!", WIDTH / 2, HEIGHT / 2);

				loose = true;
				gogo = true;
				this.endDrawScreen(g); // 끝났을 떄 그림을 초기화 시킨다.
			}
		}

		// 아직 승리하지 않았을때
		if (gogo == false)
			repaint(); // 계속해서 그려준다.

	}

	private void endDrawScreen(Graphics g) {
		// TODO Auto-generated method stub
		// 현제 드로윙을 없에준다.
		dbImage = createImage(WIDTH, HEIGHT);
		dbg = dbImage.getGraphics();

		g.drawImage(dbImage, 0, 0, 0, 0, this);

//		this.add(new EndViewer(this)); 
		this.change("endViewer"); // test

	}
	
	// 패널 변경시켜주기 
	public void change(String panelName) {
		// TODO Auto-generated method stub
		
		if (panelName.equals("endViewer")) {
			getContentPane().removeAll();
			this.endViewer = new EndViewer(this); // 여기 프레임을 보낸다
			getContentPane().add(this.endViewer);
			revalidate(); // 다시 유요하게 만든다 .

		} else if (panelName.equals("showRank")) {
			getContentPane().removeAll();
			this.showRank = new ShowRank(this);
			getContentPane().add(this.showRank);
			revalidate();
			
		} else if(panelName.equals("backButton")) {
			getContentPane().removeAll();
			this.endViewer = new EndViewer(this);
			getContentPane().add(this.endViewer);
			revalidate();
			
		}else if(panelName.equals("insertRank")){
			getContentPane().removeAll();
			this.insertRank = new InsertRank(this);
			getContentPane().add(this.insertRank);
			revalidate();
		}else if(panelName.equals("retry")) {
			getContentPane().removeAll();
			new Main();
			this.dispose(); //다시 로그인 창으로 ...
		}

	}
	

//	// 메인 함수
//	public static void main(String[] args) {
//
//		// 클래스 인스턴스 객체 선언
//		DoubleBuffering db = new DoubleBuffering();
//		// Enemy obj = new Enemy(db);
//		// 쓰레드 실행
//		Thread t1 = new Thread(db);
//		// Thread t2 = new Thread(obj);
//		t1.start();
//		// t2.start();
//
//	}
}