# TeleAccess Pro
## Author
1. Hoang Manh Dung

- Email: dungmanh74@gmail.com

- GitHub: [mdunggggg](https://github.com/mdunggggg)

- MSV: B21DCCN268

2. Nguyen Anh Duc

- Email: nguyenanhduchatake2003@gmail.com

- GitHub: [Shikamii](https://github.com/Shikamii)

- MSV: B21DCCN244

3. Phung Ngoc Quy

- Email: phungngocquy2003@gmail.com

- GitHub: [ngocquys](https://github.com/ngocquys)

- MSV: B21DCCN638

## Overview
This project demonstrates how to build a Remote Desktop Control system, featuring several key components:.
- **Remote Access**: Allows users to connect to a remote computer and interact with it as if they were physically present.
Full control over the remote machine’s screen, enabling seamless operations from a distance.
- **Voice Chat**: Integrated voice chat functionality enables real-time verbal communication between the remote user (client) and the host. Facilitates collaboration and troubleshooting without needing separate communication tools.
- **File Transfer**: Securely transfer files between the client and server, enabling easy sharing of data and documents.
Supports fast and efficient file sharing to enhance productivity during remote sessions

## Technology
- **TCP Socket**: This project uses socket technology to enable real-time communication for key features like Remote Access, where the server streams the desktop view, Voice-Chat and File Transfer for secure and efficient file sharing during remote sessions.
- **RMI**: is used to handle events from the client side, including mouse and keyboard control on the server. With RMI, the client can send control commands directly to the server through remote methods, enabling smooth and straightforward device control
- **JavaFx**: Provides a rich graphical user interface for the client application. It enhances the user experience by creating a visually appealing and interactive interface, making remote access, chat, and file transfer functionalities intuitive and user-friendly.
- **jBCrypt**: Utilizes BCrypt for password hashing, ensuring secure authentication when connecting to the remote machine. This feature enhances system security by encrypting user passwords, preventing unauthorized access and safeguarding sensitive information during remote sessions.
## Requirements
- JDK 21 or above

## License
This project is licensed under the MIT License - see the [LICENSE](/License) file for details.
